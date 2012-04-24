package uk.co.vurt.taskhelper.activities;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.task.Page;
import uk.co.vurt.hakken.domain.task.pageitem.PageItem;
import uk.co.vurt.taskhelper.R;
import uk.co.vurt.taskhelper.processor.JobProcessor;
import uk.co.vurt.taskhelper.ui.WidgetFactory;
import uk.co.vurt.taskhelper.ui.widget.LabelledCheckBox;
import uk.co.vurt.taskhelper.ui.widget.LabelledDatePicker;
import uk.co.vurt.taskhelper.ui.widget.LabelledEditBox;
import uk.co.vurt.taskhelper.ui.widget.LabelledSpinner;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RunJob extends Activity {

	private static final String TAG = "RunJob";

	// Shouldn't this be an enum?
	private static final int STATE_RUN = 0;

	private int state;

	private ContentResolver contentResolver;

	private JobProcessor jobProcessor;
	private Map<String, View> widgetMap;

	private LinearLayout pageContent;
	private LinearLayout buttonBar;

	private Button finishButton;
	private Button nextButton;
	private Button previousButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		contentResolver = getContentResolver();
		final Intent intent = getIntent();
		final String action = intent.getAction();
		if (Intent.ACTION_RUN.equals(action)) {
			state = STATE_RUN;
		} else {
			Log.e(TAG, "Unknown action '" + intent.getAction() + "'. Exiting.");
			finish();
			return;
		}

		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.run_task);
		// TODO: Make this dynamic, based on the task definition type
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
				R.drawable.hsc_logo);

		buttonBar = (LinearLayout) findViewById(R.id.buttonBar);
		pageContent = (LinearLayout) findViewById(R.id.pageContent);

		
		// Get the job definition
		jobProcessor = new JobProcessor(contentResolver, intent.getData());

		widgetMap = new HashMap<String, View>();

		// Setup buttons
		previousButton = new Button(this);
		previousButton.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		previousButton.setText("Previous");
		previousButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View view) {
				savePage(jobProcessor.getCurrentPage());
				jobProcessor.previousPage();
				drawPage();
				return;
			}

		});

		nextButton = new Button(this);
		nextButton.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		nextButton.setText("Next");
		nextButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View view) {
				savePage(jobProcessor.getCurrentPage());
				jobProcessor.nextPage();
				drawPage();
				return;
			}

		});

		finishButton = new Button(this);
		finishButton.setLayoutParams(new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT));
		finishButton.setText("Finish");
		finishButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View view) {
				savePage(jobProcessor.getCurrentPage());
				finishJob();
				return;
			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		drawPage();
	}

	protected void finishJob() {
		jobProcessor.finish();
		RunJob.this.finish();
	}

	protected void savePage(Page page) {
		Log.d(TAG, "Saving page: " + page);
		List<PageItem> items = page.getItems();
		for (PageItem item : items) {
			String widgetKey = createWidgetKey(jobProcessor.getPageName(), item);
			View widget = widgetMap.get(widgetKey);

			if (widget != null) {
				String value = null;
				if ("TEXT".equals(item.getType())) {
					LabelledEditBox editBox = (LabelledEditBox) widget;
					value = editBox.getValue();
				} else if ("DIGITS".equals(item.getType())) {
					LabelledEditBox editBox = (LabelledEditBox) widget;
					value = editBox.getValue();
				} else if ("DATETIME".equals(item.getType())) {
					LabelledDatePicker datePicker = (LabelledDatePicker) widget;
					value = datePicker.getValue();
				} else if ("YESNO".equals(item.getType())) {
					LabelledCheckBox checkBox = (LabelledCheckBox) widget;
					value = Boolean.toString(checkBox.getValue());
				} else if ("SELECT".equals(item.getType())) {
					LabelledSpinner spinner = (LabelledSpinner) widget;
					if (spinner.isMultiSelect()) {
						// serialise values to a comma separated list.
						String[] values = spinner.getSelectedValues();
						StringBuffer valueBuffer = new StringBuffer();
						for (int i = 0; i < values.length; i++) {
							valueBuffer.append(values[i]);
							if (i < values.length - 1) {
								valueBuffer.append(",");
							}
						}
						value = valueBuffer.toString();
						valueBuffer = null;
					} else {
						value = spinner.getSelectedValue();
					}

				}
				if (value != null) {
					DataItem dataItem = new DataItem(page.getName(),
							item.getName(), item.getType(), value);
					Uri dataItemUri = jobProcessor.storeDataItem(dataItem);
					Log.d(TAG, "Stored dataitem: " + dataItemUri);
				}
			} else {
				Log.e(TAG, "Unable to retrieve widget with key: " + widgetKey);
			}
		}

	}

	private DataItem retrieveDataItem(String pageName, String name, String type) {
		return jobProcessor.retrieveDataItem(pageName, name, type);
	}

	private String createWidgetKey(String pageName, PageItem item) {
		return pageName + "_" + item.getName() + "_" + item.getType();
	}

	protected void drawPage() {

		Log.d(TAG, "state: " + state);
		if (state == STATE_RUN) {

			pageContent.removeAllViewsInLayout();

			setTitle(jobProcessor.getPageName());

			List<PageItem> items = jobProcessor.getPageItems();

			if (items != null) {
				Log.d(TAG, "Items: " + items.size());
				for (PageItem item : items) {
					Log.d(TAG, "Current item: " + item);
					String widgetKey = createWidgetKey(
							jobProcessor.getPageName(), item);

					View widget = null;
					if (widgetMap.containsKey(widgetKey)) {
						// retrieve widget from map
						widget = widgetMap.get(widgetKey);
					} else {
						// new widget, so create it
						widget = WidgetFactory.createWidget(
								this,
								item,
								retrieveDataItem(jobProcessor.getPageName(),
												 item.getName(), 
												 item.getType()));

						// TODO: Figure out better way of handling this,
						// hopefully within the widget factory itself.
						if ("DATETIME".equals(item.getType())) {
							final LabelledDatePicker datePicker = ((LabelledDatePicker) widget);
							datePicker
									.setOnClickListener(new View.OnClickListener() {
										@Override
										public void onClick(View v) {
											showDatePickerDialog(datePicker);
										}
									});
						}

						widgetMap.put(widgetKey, widget);
					}

					pageContent.addView(widget);
				}
			} else {
				TextView errorLabel = new TextView(this);
				errorLabel.setText("No items were defined for this page.");
				pageContent.addView(errorLabel);
				Log.d(TAG, "No items defined for this page.");
			}

			// define next/previous/finish buttons
			buttonBar.removeAllViewsInLayout();

			if (jobProcessor.previousPages()) {
				buttonBar.addView(previousButton);
			}
			if (jobProcessor.morePages()) {
				if (jobProcessor.lastPage()) {
					buttonBar.addView(finishButton);
				} else {
					buttonBar.addView(nextButton);
				}
			}

		}
	}

	static final int DATE_DIALOG_ID = 0;

	private int mYear;
	private int mMonth;
	private int mDay;

	private LabelledDatePicker currentDatePicker;

	private void showDatePickerDialog(LabelledDatePicker datePicker) {
		currentDatePicker = datePicker;
		// TODO: Extract default date value from labelled date picker
		Calendar cal = Calendar.getInstance();
		mYear = cal.get(Calendar.YEAR);
		mMonth = cal.get(Calendar.MONTH);
		mDay = cal.get(Calendar.DAY_OF_MONTH);
		showDialog(DATE_DIALOG_ID);
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	private void updateDisplay() {
		currentDatePicker.setValue(new StringBuilder().append(mDay).append("-")
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mYear).append(" ")
				.toString());
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}
}
