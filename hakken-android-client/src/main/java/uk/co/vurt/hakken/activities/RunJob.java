package uk.co.vurt.hakken.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import net.wmfs.coalesce.csql.ExpressionException;

import uk.co.vurt.hakken.R;
import uk.co.vurt.hakken.domain.job.DataItem;
import uk.co.vurt.hakken.domain.task.Page;
import uk.co.vurt.hakken.domain.task.pageitem.PageItem;
import uk.co.vurt.hakken.processor.JobProcessor;
import uk.co.vurt.hakken.processor.PageItemProcessor;
import uk.co.vurt.hakken.ui.WidgetFactory;
import uk.co.vurt.hakken.ui.widget.LabelledCheckBox;
import uk.co.vurt.hakken.ui.widget.LabelledDatePicker;
import uk.co.vurt.hakken.ui.widget.LabelledEditBox;
import uk.co.vurt.hakken.ui.widget.LabelledSpinner;
import uk.co.vurt.hakken.ui.widget.WidgetWrapper;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RunJob extends Activity {

	private static final String TAG = "RunJob";
	private static final String WIDGET_MAP_KEY = TAG + "-widget_map";

	// Shouldn't this be an enum?
	private static final int STATE_RUN = 0;

	private int state;

	private ContentResolver contentResolver;

	private JobProcessor jobProcessor;
	private HashMap<String, WidgetWrapper> widgetWrapperMap;

	private LinearLayout pageContent;

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

		pageContent = (LinearLayout) findViewById(R.id.pageContent);

		// Get the job definition
		if (savedInstanceState != null) {
			jobProcessor = new JobProcessor(contentResolver, intent.getData(),
					savedInstanceState);
		} else {
			jobProcessor = new JobProcessor(contentResolver, intent.getData());
		}

		if (savedInstanceState != null
				&& savedInstanceState.containsKey(WIDGET_MAP_KEY)) {
			widgetWrapperMap = (HashMap) savedInstanceState
					.getSerializable(WIDGET_MAP_KEY);
		} else {
			widgetWrapperMap = new HashMap<String, WidgetWrapper>();
		}

		// Setup buttons
		previousButton = (Button) findViewById(R.id.previousButton);
		nextButton = (Button) findViewById(R.id.nextButton);
		finishButton = (Button) findViewById(R.id.finishButton);

		previousButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View view) {
				savePage(jobProcessor.getCurrentPage());
				jobProcessor.previousPage();
				drawPage(true);
				return;
			}

		});

		nextButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View view) {
				if (saveAndValidatePage(jobProcessor.getCurrentPage())) {
					jobProcessor.nextPage();
				}
				drawPage(false);
				return;
			}

		});

		finishButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View view) {
				if (saveAndValidatePage(jobProcessor.getCurrentPage())) {
					finishJob();
				} else {
					drawPage(false);
				}
				return;
			}

		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		drawPage(true); // should we ignore errors here?
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		jobProcessor.saveInstanceState(outState);
		if (widgetWrapperMap != null) {
			for (WidgetWrapper wrapper : widgetWrapperMap.values()) {
				if (wrapper.getWidget() != null
						&& wrapper.getWidget().getParent() != null) {
					((ViewGroup) wrapper.getWidget().getParent())
							.removeView(wrapper.getWidget());
				}
			}
			outState.putSerializable(WIDGET_MAP_KEY, widgetWrapperMap);
		}
	}

	protected void finishJob() {
		jobProcessor.finish();
		RunJob.this.finish();
	}

	private void showErrorPopUp(String title, String errorMessage) {
		Log.d(TAG, "Creating error dialog: " + title + ": " + errorMessage);

		AlertDialog.Builder errorBuilder = new AlertDialog.Builder(this);
		errorBuilder.setTitle(title);
		errorBuilder.setMessage(errorMessage);
		errorBuilder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Do nothing but close the dialog
					}
				});

		AlertDialog errorDialog = errorBuilder.create();
		errorDialog.show();
	}

	private void showErrorPopUp(String errorMessage) {
		showErrorPopUp("Validation Error", errorMessage);
	}

	protected void savePage(Page page) {
		// simply ignore the validation status returned
		saveAndValidatePage(page);
	}

	private List<String> missingValues;

	protected boolean saveAndValidatePage(Page page) {
		boolean valid = true;
		missingValues = new ArrayList<String>();

		Log.d(TAG, "Saving page: " + page);
		List<PageItem> items = page.getItems();
		for (PageItem item : items) {
			String widgetKey = createWidgetKey(jobProcessor.getPageName(), item);
			WidgetWrapper wrapper = widgetWrapperMap.get(widgetKey);
			View widget = wrapper.getWidget();

			if (!wrapper.isReadOnly()) {
				if (widget != null) {
					String value = null;
					if ("TEXT".equals(item.getType())) {
						LabelledEditBox editBox = (LabelledEditBox) widget;
						value = editBox.getValue();
					} else if ("DIGITS".equals(item.getType())
							|| "NUMERIC".equals(item.getType())) {
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

					// Not sure if this it the best place to do this but...
					// is there an expression we need to evaluate for the value?
					String condition = PageItemProcessor.getStringAttribute(item, "condition");
					boolean useExpression = true;
					if(condition != null){
						try {
							useExpression = jobProcessor.evaluateCondition(condition);
						} catch (ExpressionException e) {
							Log.w(TAG, "Unable to evaluate condition for " + item.getName());
						}
					}
					String expression = PageItemProcessor.getStringAttribute(
							item, "expression");
					if (expression != null && useExpression) {
						value = jobProcessor.evaluateExpression(expression);
					}

					if (value != null) {

						// compare to previous value
						DataItem previousValue = retrieveDataItem(
								jobProcessor.getPageName(), item.getName(),
								item.getType());

						// only store a dataitem if the value has changed.
						if ((previousValue == null)
								|| (previousValue != null && (!previousValue
										.getValue().equals(value)))) {
							Log.d(TAG,
									"Previous value: "
											+ (previousValue != null ? previousValue
													.getValue() : "null")
											+ " New value: " + value);

							DataItem dataItem = new DataItem(page.getName(),
									item.getName(), item.getType(), value);
							Uri dataItemUri = jobProcessor
									.storeDataItem(dataItem);
							Log.d(TAG, "Stored dataitem: " + dataItemUri);
						}
					}

					if (wrapper.isRequired()) {
						boolean checkValue = true;

						if (wrapper.hasCondition()) {
							try {
								checkValue = jobProcessor
										.evaluateCondition(wrapper
												.getCondition());
							} catch (ExpressionException e) {
								Log.e(TAG, "Unable to evaluate condition", e);
							}
						}
						if (checkValue
								&& (value == null || value.length() <= 0)) {
							missingValues.add(item.getLabel());
							valid = false;
						}
						// valid = valid & (value != null);
					}

				} else {
					Log.e(TAG, "Unable to retrieve widget with key: "
							+ widgetKey);
				}
			}
		}
		Log.d(TAG, "page valid: " + valid);
		return valid;
	}

	private DataItem retrieveDataItem(String pageName, String name, String type) {
		return jobProcessor.retrieveDataItem(pageName, name, type);
	}

	private String createWidgetKey(String pageName, PageItem item) {
		return pageName + "_" + item.getName() + "_" + item.getType();
	}

	protected void drawPage(boolean ignoreErrors) {

		Log.d(TAG, "state: " + state);
		if (state == STATE_RUN) {

			if (jobProcessor.isCurrentPageVisible()) {

				pageContent.removeAllViewsInLayout();

				setTitle(jobProcessor.getPageTitle());

				List<PageItem> items = jobProcessor.getPageItems();

				if (items != null) {
					Log.d(TAG, "Items: " + items.size());
					for (PageItem item : items) {
						Log.d(TAG, "Current item: " + item);
						String widgetKey = createWidgetKey(
								jobProcessor.getPageName(), item);

						WidgetWrapper wrapper = null;
						if (widgetWrapperMap.containsKey(widgetKey)) {
							// retrieve widget from map
							wrapper = widgetWrapperMap.get(widgetKey);
						} else {
							// new widget, so create it
							wrapper = WidgetFactory.createWidget(
									this,
									item,
									retrieveDataItem(
											jobProcessor.getPageName(),
											item.getName(), item.getType()));

							// TODO: Figure out better way of handling this,
							// hopefully within the widget factory itself.
							if ("DATETIME".equals(item.getType())) {
								final LabelledDatePicker datePicker = ((LabelledDatePicker) wrapper
										.getWidget());
								datePicker
										.setOnClickListener(new View.OnClickListener() {
											@Override
											public void onClick(View v) {
												showDatePickerDialog(datePicker);
											}
										});
							}
							widgetWrapperMap.put(widgetKey, wrapper);
						}
						if (!wrapper.isHidden()) {
							pageContent.addView(wrapper.getWidget());
						}
					}
				} else {
					TextView errorLabel = new TextView(this);
					errorLabel.setText("No items were defined for this page.");
					pageContent.addView(errorLabel);
					Log.d(TAG, "No items defined for this page.");
				}

				if (jobProcessor.previousPages()) {
					previousButton.setVisibility(View.VISIBLE);
				} else {
					previousButton.setVisibility(View.GONE);
				}
				if (jobProcessor.morePages() && !jobProcessor.lastPage()) {
					Log.d(TAG, "There are more pages; hiding the finish buttons and showing the next button");
					nextButton.setVisibility(View.VISIBLE);
					finishButton.setVisibility(View.GONE);
				} else {
					Log.d(TAG, "No more pages; hiding the next button and showing the finish button");
					finishButton.setVisibility(View.VISIBLE);
					nextButton.setVisibility(View.GONE);
				}

				// display error message if validation errors exists
				if (!jobProcessor.previousPages()
						&& jobProcessor.showServerError()) {
					showErrorPopUp("Server Error",
							"This job could not be submitted because of the following server error:\n\n"
									+ jobProcessor.getServerError());
				} else if (!ignoreErrors && missingValues != null
						&& missingValues.size() > 0) {
					StringBuffer errorBuffer = new StringBuffer(
							"The following fields require a value:\n");
					for (String missingField : missingValues) {
						errorBuffer.append(" ");
						errorBuffer.append(missingField);
						errorBuffer.append("\n");
					}
					showErrorPopUp(errorBuffer.toString());
				}

			} else {
				// skip the current page
				jobProcessor.nextPage();
				drawPage(true);
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
