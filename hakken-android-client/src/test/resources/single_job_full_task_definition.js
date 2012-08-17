[ {
	"id" : 61057,
	"name" : "HSC/22231 - Status: Appointment",
	"definition" : {
		"id" : 0,
		"name" : "hfrc_assessment",
		"description" : "HFRC Workbook",
		"pages" : [
				{
					"name" : "intro",
					"title" : "Information",
					"items" : [ {
						"name" : "contact_title",
						"label" : "Title",
						"type" : "TEXT",
						"value" : "",
						"values" : null,
						"attributes" : {
							"readonly" : "true"
						}
					}, {
						"name" : "contact_first_name",
						"label" : "First Name",
						"type" : "TEXT",
						"value" : "",
						"values" : null,
						"attributes" : {
							"readonly" : "true"
						}
					}, {
						"name" : "contact_last_name",
						"label" : "Last Name",
						"type" : "TEXT",
						"value" : "",
						"values" : null,
						"attributes" : {
							"readonly" : "true"
						}
					}, {
						"name" : "address",
						"label" : "Address",
						"type" : "TEXT",
						"value" : "",
						"values" : null,
						"attributes" : {
							"readonly" : "true"
						}
					}, {
						"name" : "password",
						"label" : "Password",
						"type" : "TEXT",
						"value" : "",
						"values" : null,
						"attributes" : {
							"readonly" : "true"
						}
					}, {
						"name" : "notes",
						"label" : "Notes",
						"type" : "TEXT",
						"value" : "",
						"values" : null,
						"attributes" : {
							"readonly" : "true"
						}
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_ASSESSMENT_DETAILS",
					"title" : "Assessment details",
					"items" : [ {
						"name" : "label_0",
						"label" : "Assessment Details",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_ACTUAL_ASSESSMENT_DATE.HSC",
						"label" : "Actual Date and time of HSC",
						"type" : "DATETIME",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_ASSESSOR_1.HSC",
						"label" : "Assessor 1 roll number",
						"type" : "TEXT",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_ASSESSOR_2.HSC",
						"label" : "Assessor 2 roll number",
						"type" : "TEXT",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_SIGNATURE",
					"title" : "Occupier Signature",
					"items" : [ {
						"name" : "label_0",
						"label" : "Was the occupier's signature obtained?",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_SIGNATURE.HSC",
						"label" : "",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_SHARE_INFO",
					"title" : "Share info",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Occupier agrees to sharing of information with Partners?",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							}, {
								"name" : "HSA_SHARE_INFO.HSC",
								"label" : "",
								"type" : "YESNO",
								"value" : null,
								"values" : null,
								"attributes" : null
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_PROPERTY",
					"title" : "Property details",
					"items" : [ {
						"name" : "tenure_type",
						"label" : "Tenure Type",
						"type" : "SELECT",
						"value" : null,
						"values" : [ {
							"label" : "Owner occupied",
							"value" : "owner_occupied"
						}, {
							"label" : "Rented - Council/Housing Association",
							"value" : "rented_ha"
						}, {
							"label" : "Rented - Private",
							"value" : "rented_private"
						}, {
							"label" : "Rented - Landlord unknown",
							"value" : "rented_unknown"
						}, {
							"label" : "Not known",
							"value" : "not_known"
						} ],
						"attributes" : null
					}, {
						"name" : "label_1",
						"label" : "Property Type",
						"type" : "SELECT",
						"value" : null,
						"values" : [ {
							"label" : "Undefined",
							"value" : "undefined"
						} ],
						"attributes" : null
					}, {
						"name" : "HSA_HMO.HSC",
						"label" : "HMO?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_SHELTERED_HOUSING.HSC",
						"label" : "Sheltered Accommodation?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_OCCUPANCY",
					"title" : "Occupancy",
					"items" : [ {
						"name" : "label_0",
						"label" : "Ethnicity",
						"type" : "SELECT",
						"value" : null,
						"values" : [ {
							"label" : "British",
							"value" : "white_british"
						}, {
							"label" : "Irish",
							"value" : "white_irish"
						}, {
							"label" : "Gypsy or Irish Traveller",
							"value" : "white_traveller"
						}, {
							"label" : "Any other White background",
							"value" : "white_other"
						}, {
							"label" : "White & Black Caribbean",
							"value" : "mixed_caribbean"
						}, {
							"label" : "White & Black African",
							"value" : "mixed_african"
						}, {
							"label" : "White & Asian",
							"value" : "mixed_asian"
						}, {
							"label" : "Other Mixed",
							"value" : "mixed_other"
						}, {
							"label" : "Indian",
							"value" : "asian_indian"
						}, {
							"label" : "Pakistani",
							"value" : "asian_pakistani"
						}, {
							"label" : "Bangladeshi",
							"value" : "asian_bangladeshi"
						}, {
							"label" : "Any other Asian background",
							"value" : "asian_other"
						}, {
							"label" : "African",
							"value" : "black_african"
						}, {
							"label" : "Caribbean",
							"value" : "black_caribbean"
						}, {
							"label" : "Any other Black background",
							"value" : "black_other"
						}, {
							"label" : "Arab",
							"value" : "other_arab"
						}, {
							"label" : "Any other ethnic background",
							"value" : "other_any"
						} ],
						"attributes" : {
							"multiselect" : "true"
						}
					}, {
						"name" : "label_1",
						"label" : "Occupancy",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_AGE_0_4.HSC",
						"label" : "0 - 4",
						"type" : "DIGITS",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_AGE_5_14.HSC",
						"label" : "5 - 14",
						"type" : "DIGITS",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_AGE_15_24.HSC",
						"label" : "15 - 24",
						"type" : "DIGITS",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_AGE_25_44.HSC",
						"label" : "25 - 44",
						"type" : "DIGITS",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_AGE_45_64.HSC",
						"label" : "45 - 64",
						"type" : "DIGITS",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_AGE_65_79.HSC",
						"label" : "65 - 79",
						"type" : "DIGITS",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_AGE_80_PLUS.HSC",
						"label" : "80+",
						"type" : "DIGITS",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_FIRE_HAZARDS",
					"title" : "Fire Hazards",
					"items" : [ {
						"name" : "label_0",
						"label" : "Fire Hazards",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_CHIP_PAN.HSC",
						"label" : "Is a chip/fat pan in use?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_ELECTRIC_BLANKETS.HSC",
						"label" : "Are electric blankets in use?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_SMOKERS.HSC",
						"label" : "Does anyone smoke inside the property?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_INAPPROPRIATE_CANDLES.HSC",
						"label" : "Are candles used inappropriately?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_CLOTHES_DRYING.HSC",
						"label" : "Are clothes dried dangerously?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_OVERLOADED_PLUG_SOCKETS.HSC",
						"label" : "Are plug sockets/adaptors overloaded?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_FIRE_HAZARDS_NOTES",
					"title" : "Fire Hazards Notes",
					"items" : [ {
						"name" : "label_0",
						"label" : "Fire Hazards: Additional Information",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_FIRE_HAZARDS_NOTES.HSC",
						"label" : "Please provide details",
						"type" : "TEXT",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_INDIVIDUAL_RISK",
					"title" : "Individual Risks",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Individual Risks",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "HSA_DIFFICULTY_RESPONDING.HSC",
								"label" : "Would anyone have difficulty responding to an emergency?",
								"type" : "YESNO",
								"value" : null,
								"values" : null,
								"attributes" : null
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_INDIVIDUAL_RISK_DETAIL",
					"title" : "Difficulty responding to an emergency",
					"items" : [ {
						"name" : "DIFF_RESP",
						"label" : "Difficulty responding to an emergency",
						"type" : "SELECT",
						"value" : null,
						"values" : [ {
							"label" : "Undefined",
							"value" : "undefined"
						} ],
						"attributes" : {
							"multiselect" : "true"
						}
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_DIFFICULTY_RESPONDING_NOTES",
					"title" : "Difficulty Responding Notes",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Difficulty Responding: Additional Information",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							}, {
								"name" : "HSA_DIFFICULTY_RESP_NOTES.HSC",
								"label" : "Please provide details",
								"type" : "TEXT",
								"value" : null,
								"values" : null,
								"attributes" : null
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_FIRE_SAFETY_TUTORING",
					"title" : "Fire Safety Tutoring",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Risks: Fire History / Fire Safety Tutoring",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "HSA_FIRE_EVIDENCE.HSC",
								"label" : "Is there evidence / history of fire (including cigarette burns)?",
								"type" : "YESNO",
								"value" : null,
								"values" : null,
								"attributes" : null
							}, {
								"name" : "HSA_FIRE_SAFETY_TUTORING.HSC",
								"label" : "Is fire safety tutoring required?",
								"type" : "YESNO",
								"value" : null,
								"values" : null,
								"attributes" : null
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_YOUTH_SERVICES",
					"title" : "Youth Services",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Youth Services",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "HSA_YOUTH_SERVICES_CONSENT.HSC",
								"label" : "Does the parent/guardian consent to being contacted by Youth Services?",
								"type" : "YESNO",
								"value" : null,
								"values" : null,
								"attributes" : null
							}, {
								"name" : "YOUTH_SERVICES_REASON.HSC",
								"label" : "Reason",
								"type" : "SELECT",
								"value" : null,
								"values" : [ {
									"label" : "Undefined",
									"value" : "undefined"
								} ],
								"attributes" : {
									"multiselect" : "true"
								}
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_OPS_INFO",
					"title" : "Additional Risks",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Additional Risks",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "ADDN_RISKS",
								"label" : "Are any of the following hazards present which may have an impact at an incident?",
								"type" : "SELECT",
								"value" : null,
								"values" : [ {
									"label" : "Undefined",
									"value" : "undefined"
								} ],
								"attributes" : {
									"multiselect" : "true"
								}
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_OPS_INFO_NOTES",
					"title" : "Additional Risk Notes",
					"items" : [ {
						"name" : "label_0",
						"label" : "Risks: Additional Information",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_ADDITIONAL_RISK_NOTES.HSC",
						"label" : "Please provide details",
						"type" : "TEXT",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_SAFETY_ADVICE",
					"title" : "Safety Advice",
					"items" : [ {
						"name" : "label_0",
						"label" : "Safety Advice/Planning",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "SAD",
						"label" : "Was the following discussed?",
						"type" : "SELECT",
						"value" : null,
						"values" : [ {
							"label" : "Undefined",
							"value" : "undefined"
						} ],
						"attributes" : {
							"multiselect" : "true"
						}
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_BEFORE_EQUIPMENT",
					"title" : "Fire Protection Equipment",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Fire Protection Equipment",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "label_1",
								"label" : "Before this HSC",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "HSA_BEFORE_CORRECT_ALARMS.HSC",
								"label" : "How many smoke alarms were working <b>and</b> correctly sited?",
								"type" : "DIGITS",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "HSA_BEFORE_INCORRECT_ALARMS.HSC",
								"label" : "How many smoke alarms were not correctly fitted <b>or</b> not sited correctly?",
								"type" : "DIGITS",
								"value" : null,
								"values" : null,
								"attributes" : null
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_EQUIPMENT_FITTED",
					"title" : "Fire Protection Equipment",
					"items" : [ {
						"name" : "label_0",
						"label" : "Fire Protection Equipment",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "label_1",
						"label" : "TODO: Implement detail block functionality",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_EDUCATIONAL_MATERIAL",
					"title" : "Safety Advice",
					"items" : [ {
						"name" : "label_0",
						"label" : "Educational Material",
						"type" : "LABEL",
						"value" : null,
						"values" : null,
						"attributes" : null
					}, {
						"name" : "HSA_EDUCATIONAL_MATERIAL.HSC",
						"label" : "Was educational material supplied?",
						"type" : "YESNO",
						"value" : null,
						"values" : null,
						"attributes" : null
					} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_ASK_CARBON_MONOXIDE",
					"title" : "Carbon Monoxide Awareness",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Carbon Monoxide Awareness",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "HSC_ASK_CARBON_MONOXIDE.HSC",
								"label" : "Would you like to add or review Carbon Monoxide questions?",
								"type" : "YESNO",
								"value" : null,
								"values" : null,
								"attributes" : null
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_CARBON_MONOXIDE",
					"title" : "Carbon Monoxide",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Carbon Monoxide",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "CO_READING.HSC",
								"label" : "Carbon Monoxide reading",
								"type" : "DIGITS",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "CO_HEATING_METHOD.HSC",
								"label" : "Gas Appliances; have the occupiers got a gas safety certificate",
								"type" : "SELECT",
								"value" : null,
								"values" : [ {
									"label" : "Undefined",
									"value" : "undefined"
								} ],
								"attributes" : null
							}, {
								"name" : "CO_COOKING_METHOD.HSC",
								"label" : "Primary cooking method",
								"type" : "SELECT",
								"value" : null,
								"values" : [ {
									"label" : "Undefined",
									"value" : "undefined"
								} ],
								"attributes" : null
							}, {
								"name" : "label_1",
								"label" : "Gas Certificate",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_VPO",
					"title" : "Vulnerable Persons",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Vulnerable Persons",
								"type" : "LABEL",
								"value" : null,
								"values" : null,
								"attributes" : null
							},
							{
								"name" : "HSA_VULNERABLE_PERSON.HSC",
								"label" : "Vulnerable person - additional actions required? (please specify)",
								"type" : "YESNO",
								"value" : null,
								"values" : null,
								"attributes" : null
							}, {
								"name" : "VPO_REFERRALS.HSC",
								"label" : "Check all that apply",
								"type" : "SELECT",
								"value" : null,
								"values" : [ {
									"label" : "Undefined",
									"value" : "undefined"
								} ],
								"attributes" : null
							} ],
					"nextPages" : null
				},
				{
					"name" : "HSC_ASSESSMENT_NOTES",
					"title" : "HFRC Notes",
					"items" : [
							{
								"name" : "label_0",
								"label" : "Notes",
								"type" : "Text",
								"value" : "Please provide any further information that may be useful in respect to this assessment, including any follow up actions that may be necessary.",
								"values" : null,
								"attributes" : {
									"readonly" : "true"
								}
							}, {
								"name" : "HSA_ASSESSMENT_NOTES.HSC",
								"label" : "",
								"type" : "TEXT",
								"value" : null,
								"values" : null,
								"attributes" : null
							} ],
					"nextPages" : null
				} ]
	},
	"created" : 1341248014799,
	"due" : 1341334380000,
	"status" : "AWAITING",
	"group" : null,
	"notes" : "123 ANY STREET, SOMETOWN, A12 3BC <b>(validated)</b>",
	"dataItems" : [ {
		"name" : "BEFORE",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "password",
		"type" : "TEXT",
		"value" : "fff",
		"pageName" : "intro"
	}, {
		"name" : "PROPERTY",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "INDIVIDUAL",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "ASSESSMENT",
		"type" : null,
		"value" : "2012-07-02T17:55:07.027+01:00",
		"pageName" : "HSC"
	}, {
		"name" : "EDUCATIONAL",
		"type" : null,
		"value" : "Y",
		"pageName" : "HSC"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "CARBON",
		"type" : null,
		"value" : "null",
		"pageName" : "HSC"
	}, {
		"name" : "SIGNATURE",
		"type" : null,
		"value" : "Y",
		"pageName" : "HSC"
	}, {
		"name" : "contact",
		"type" : null,
		"value" : "262",
		"pageName" : "intro"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "ASSESSMENT",
		"type" : null,
		"value" : "null",
		"pageName" : "HSC"
	}, {
		"name" : "OCCUPANCY",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "SHARE",
		"type" : null,
		"value" : "Y",
		"pageName" : "HSC"
	}, {
		"name" : "CARBON",
		"type" : null,
		"value" : "null",
		"pageName" : "HSC"
	}, {
		"name" : "OCCUPANCY",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "address",
		"type" : "TEXT",
		"value" : "null",
		"pageName" : "intro"
	}, {
		"name" : "OPS",
		"type" : null,
		"value" : "null",
		"pageName" : "HSC"
	}, {
		"name" : "ASSESSMENT",
		"type" : null,
		"value" : "null",
		"pageName" : "HSC"
	}, {
		"name" : "PROPERTY",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "DIFFICULTY",
		"type" : null,
		"value" : "null",
		"pageName" : "HSC"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "OCCUPANCY",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "ASK",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "OCCUPANCY",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "OCCUPANCY",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "contact",
		"type" : null,
		"value" : "Paterson",
		"pageName" : "intro"
	}, {
		"name" : "OCCUPANCY",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "CARBON",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "contact",
		"type" : null,
		"value" : "Giles",
		"pageName" : "intro"
	}, {
		"name" : "OCCUPANCY",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "VPO",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "BEFORE",
		"type" : null,
		"value" : "0",
		"pageName" : "HSC"
	}, {
		"name" : "ASSESSMENT",
		"type" : null,
		"value" : "null",
		"pageName" : "HSC"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "null",
		"pageName" : "HSC"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "YOUTH",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	}, {
		"name" : "FIRE",
		"type" : null,
		"value" : "N",
		"pageName" : "HSC"
	} ],
	"modified" : false
} ]
