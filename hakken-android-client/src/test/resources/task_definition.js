[

    {
        "id": 1,
        "name": "hfrc_assessment",
        "description": "HFRC Workbook",
        "pages": [
            {
                "name": "intro",
                "title": "Information",
                "items": [
                    {
                        "name": "contact_first_name",
                        "label": "First Name",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    },
                    {
                        "name": "contact_last_name",
                        "label": "Last Name",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    },
                    {
                        "name": "address",
                        "label": "Address",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    },
                    {
                        "name": "password",
                        "label": "Password",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    },
                    {
                        "name": "notes",
                        "label": "Notes",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_ASSESSMENT_DETAILS",
                "title": "Assessment details",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Assessment Details",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_ACTUAL_ASSESSMENT_DATE.HSC",
                        "label": "Actual Date and time of HSC",
                        "type": "DATETIME",
                        "value": null,
                        "values": null,
                        "attributes": {
                            "hidden": "true",
                            "expression": ":NOW"
                        }
                    },
                    {
                        "name": "HSA_AGENT_ID.HSC",
                        "label": "Completed by",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Station based personnel",
                                "value": "85996"
                            },
                            {
                                "label": "Non-station based personnel",
                                "value": "85997"
                            }
                        ],
                        "attributes": {
                            "required": "true"
                        }
                    },
                    {
                        "name": "HSA_ASSESSOR_1.HSC",
                        "label": "Assessor 1 roll number",
                        "type": "DIGITS",
                        "value": null,
                        "values": null,
                        "attributes": {
                            "required": "true"
                        }
                    },
                    {
                        "name": "HSA_ASSESSOR_2.HSC",
                        "label": "Assessor 2 roll number",
                        "type": "DIGITS",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_ASSESSMENT_STATION",
                "title": "Assessment Station",
                "items": [
                    {
                        "name": "label_0",
                        "label": "The crew's home station or, for non-station based personnel, the station area where the HFRC has taken place.",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_STATION.HSC",
                        "label": "Station",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Abbots Bromley",
                                "value": "22"
                            },
                            {
                                "label": "Ashley",
                                "value": "10"
                            },
                            {
                                "label": "Barton under Needwood",
                                "value": "23"
                            },
                            {
                                "label": "Biddulph",
                                "value": "5"
                            },
                            {
                                "label": "Brewood",
                                "value": "32"
                            },
                            {
                                "label": "Burslem",
                                "value": "12"
                            },
                            {
                                "label": "Burton on Trent",
                                "value": "19"
                            },
                            {
                                "label": "[Burton Technician]",
                                "value": "39"
                            },
                            {
                                "label": "Cannock",
                                "value": "24"
                            },
                            {
                                "label": "[Cannock Technician]",
                                "value": "43"
                            },
                            {
                                "label": "Chase Terrace",
                                "value": "27"
                            },
                            {
                                "label": "Cheadle",
                                "value": "7"
                            },
                            {
                                "label": "Codsall",
                                "value": "30"
                            },
                            {
                                "label": "Eccleshall",
                                "value": "17"
                            },
                            {
                                "label": "Gnosall",
                                "value": "18"
                            },
                            {
                                "label": "Hanley",
                                "value": "11"
                            },
                            {
                                "label": "HQ",
                                "value": "2"
                            },
                            {
                                "label": "Ipstones",
                                "value": "6"
                            },
                            {
                                "label": "Kidsgrove",
                                "value": "9"
                            },
                            {
                                "label": "Kinver",
                                "value": "34"
                            },
                            {
                                "label": "Leek",
                                "value": "3"
                            },
                            {
                                "label": "Lichfield",
                                "value": "26"
                            },
                            {
                                "label": "[Lichfield Technician]",
                                "value": "40"
                            },
                            {
                                "label": "Longnor",
                                "value": "4"
                            },
                            {
                                "label": "Longton",
                                "value": "13"
                            },
                            {
                                "label": "[Moorlands Technician]",
                                "value": "38"
                            },
                            {
                                "label": "Newcastle",
                                "value": "8"
                            },
                            {
                                "label": "[Newcastle Technician]",
                                "value": "37"
                            },
                            {
                                "label": "Penkridge",
                                "value": "31"
                            },
                            {
                                "label": "Rising Brook",
                                "value": "1"
                            },
                            {
                                "label": "Rugeley",
                                "value": "25"
                            },
                            {
                                "label": "Sandyford",
                                "value": "14"
                            },
                            {
                                "label": "[South Technician]",
                                "value": "42"
                            },
                            {
                                "label": "Stafford",
                                "value": "15"
                            },
                            {
                                "label": "[Stafford Technician]",
                                "value": "44"
                            },
                            {
                                "label": "[Stoke Technician]",
                                "value": "36"
                            },
                            {
                                "label": "Stone",
                                "value": "16"
                            },
                            {
                                "label": "Tamworth Belgrave",
                                "value": "28"
                            },
                            {
                                "label": "Tamworth Mercia",
                                "value": "29"
                            },
                            {
                                "label": "[Tamworth Technician]",
                                "value": "41"
                            },
                            {
                                "label": "[TBC Technician]",
                                "value": "45"
                            },
                            {
                                "label": "Tutbury",
                                "value": "21"
                            },
                            {
                                "label": "Uttoxeter",
                                "value": "20"
                            },
                            {
                                "label": "Wombourne",
                                "value": "33"
                            }
                        ],
                        "attributes": {
                            "required": "true"
                        }
                    }
                ],
                "nextPages": [
                    {
                        "pageName": "HSC_ASSESSMENT_WATCH",
                        "condition": ":dataitem.HSC_ASSESSMENT_DETAILS$HSA_AGENT_ID.HSC$SELECT = '85996'"
                    },
                    {
                        "pageName": "HSC_SIGNATURE",
                        "condition": ""
                    }
                ]
            },
            {
                "name": "HSC_ASSESSMENT_WATCH",
                "title": "Assessment Watch",
                "items": [
                    {
                        "name": "label_0",
                        "label": "The watch responsible for this HFRC.",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_WATCH.HSC",
                        "label": "Select a watch",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Red",
                                "value": "RED"
                            },
                            {
                                "label": "White",
                                "value": "WHITE"
                            },
                            {
                                "label": "Blue",
                                "value": "BLUE"
                            },
                            {
                                "label": "Green",
                                "value": "GREEN"
                            },
                            {
                                "label": "Orange",
                                "value": "ORANGE"
                            },
                            {
                                "label": "T.R.V.",
                                "value": "T.R.V."
                            }
                        ],
                        "attributes": {
                            "required": "true"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_SIGNATURE",
                "title": "Occupier Signature",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Was the occupier's signature obtained?",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_SIGNATURE.HSC",
                        "label": "",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_SHARE_INFO",
                "title": "Share info",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Occupier agrees to sharing of information with Partners?",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_SHARE_INFO.HSC",
                        "label": "",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_PROPERTY",
                "title": "Property details",
                "items": [
                    {
                        "name": "HSA_TENURE_ID.HSC",
                        "label": "Tenure Type",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Owner occupied",
                                "value": "4378"
                            },
                            {
                                "label": "Rented - Council",
                                "value": "4379"
                            },
                            {
                                "label": "Rented - Private",
                                "value": "4380"
                            },
                            {
                                "label": "Rented - Housing Association",
                                "value": "4381"
                            },
                            {
                                "label": "Rented - Landlord unknown",
                                "value": "4382"
                            },
                            {
                                "label": "Not known",
                                "value": "4383"
                            }
                        ],
                        "attributes": {
                            "required": "true"
                        }
                    },
                    {
                        "name": "HSA_PROPERTY_TYPE_ID.HSC",
                        "label": "Property Type",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "House",
                                "value": "86023"
                            },
                            {
                                "label": "Flat",
                                "value": "86024"
                            },
                            {
                                "label": "Maisonette",
                                "value": "86025"
                            },
                            {
                                "label": "Halls of Residence",
                                "value": "86026"
                            },
                            {
                                "label": "Mobile Home",
                                "value": "86027"
                            },
                            {
                                "label": "Bungalow",
                                "value": "150007"
                            }
                        ],
                        "attributes": {
                            "required": "true"
                        }
                    },
                    {
                        "name": "HSA_HMO.HSC",
                        "label": "HMO?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_SHELTERED_HOUSING.HSC",
                        "label": "Sheltered Accommodation?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_OCCUPANCY",
                "title": "Occupancy",
                "items": [
                    {
                        "name": "HSA_ETHNICITY_ID.HSC",
                        "label": "Ethnicity",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Occupier does not wish to share",
                                "value": "86222"
                            },
                            {
                                "label": "White British",
                                "value": "86033"
                            },
                            {
                                "label": "White Irish",
                                "value": "86034"
                            },
                            {
                                "label": "White Gypsy or Irish Traveller",
                                "value": "86035"
                            },
                            {
                                "label": "Any other White background",
                                "value": "86036"
                            },
                            {
                                "label": "White & Black Caribbean",
                                "value": "86038"
                            },
                            {
                                "label": "White & Black African",
                                "value": "86039"
                            },
                            {
                                "label": "White & Asian",
                                "value": "86040"
                            },
                            {
                                "label": "Other Mixed",
                                "value": "86041"
                            },
                            {
                                "label": "Indian",
                                "value": "86043"
                            },
                            {
                                "label": "Pakistani",
                                "value": "86044"
                            },
                            {
                                "label": "Bangladeshi",
                                "value": "86045"
                            },
                            {
                                "label": "Chinese",
                                "value": "86046"
                            },
                            {
                                "label": "Any other Asian background",
                                "value": "86047"
                            },
                            {
                                "label": "African",
                                "value": "86049"
                            },
                            {
                                "label": "Caribbean",
                                "value": "86050"
                            },
                            {
                                "label": "Any other Black background",
                                "value": "86051"
                            },
                            {
                                "label": "Arab",
                                "value": "86056"
                            },
                            {
                                "label": "Any other ethnic background",
                                "value": "86057"
                            }
                        ],
                        "attributes": {
                            "required": "true"
                        }
                    },
                    {
                        "name": "label_1",
                        "label": "Occupancy",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_AGE_0_4.HSC",
                        "label": "0 - 4",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_AGE_5_14.HSC",
                        "label": "5 - 14",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_AGE_15_24.HSC",
                        "label": "15 - 24",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_AGE_25_44.HSC",
                        "label": "25 - 44",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_AGE_45_64.HSC",
                        "label": "45 - 64",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_AGE_65_79.HSC",
                        "label": "65 - 79",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_AGE_80_PLUS.HSC",
                        "label": "80+",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_FIRE_HAZARDS",
                "title": "Fire Hazards",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Fire Hazards",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_CHIP_PAN.HSC",
                        "label": "Is a chip/fat pan in use?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_ELECTRIC_BLANKETS.HSC",
                        "label": "Are electric blankets in use?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_SMOKERS.HSC",
                        "label": "Does anyone smoke inside the property?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_INAPPROPRIATE_CANDLES.HSC",
                        "label": "Are candles used inappropriately?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_CLOTHES_DRYING.HSC",
                        "label": "Are clothes dried dangerously?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_OVERLOADED_PLUG_SOCKETS.HSC",
                        "label": "Are plug sockets/adaptors overloaded?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_FIRE_HAZARDS_NOTES",
                "title": "Fire Hazards Notes",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Fire Hazards: Additional Information",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_FIRE_HAZARDS_NOTES.HSC",
                        "label": "Please provide details",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_INDIVIDUAL_RISK",
                "title": "Individual Risks",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Individual Risks",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_DIFFICULTY_RESPONDING.HSC",
                        "label": "Would anyone have difficulty responding to an emergency?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": [
                    {
                        "pageName": "HSC_INDIVIDUAL_RISK_DETAIL",
                        "condition": ":dataitem.HSC_INDIVIDUAL_RISK$HSA_DIFFICULTY_RESPONDING.HSC$YESNO = 'true'"
                    },
                    {
                        "pageName": "HSC_FIRE_SAFETY_TUTORING",
                        "condition": ""
                    }
                ]
            },
            {
                "name": "HSC_INDIVIDUAL_RISK_DETAIL",
                "title": "Difficulty responding to an emergency",
                "items": [
                    {
                        "name": "DIFF_RESP",
                        "label": "Difficulty responding to an emergency",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Mobility (A) Slow to vacate",
                                "value": "86283"
                            },
                            {
                                "label": "Mobility (B) Requires assistance to vacate",
                                "value": "86284"
                            },
                            {
                                "label": "Mobility (C) Requires assistance / special equipment",
                                "value": "86285"
                            },
                            {
                                "label": "Vision",
                                "value": "86286"
                            },
                            {
                                "label": "Hearing",
                                "value": "86287"
                            },
                            {
                                "label": "Other",
                                "value": "86288"
                            }
                        ],
                        "attributes": {
                            "multiselect": "true",
                            "type_id": "86282"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_DIFFICULTY_RESPONDING_NOTES",
                "title": "Difficulty Responding Notes",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Difficulty Responding: Additional Information",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_DIFFICULTY_RESP_NOTES.HSC",
                        "label": "Please provide details",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_FIRE_SAFETY_TUTORING",
                "title": "Fire Safety Tutoring",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Risks: Fire History / Fire Safety Tutoring",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_FIRE_EVIDENCE.HSC",
                        "label": "Is there evidence / history of fire (including cigarette burns)?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_FIRE_SAFETY_TUTORING.HSC",
                        "label": "Is fire safety tutoring required?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": [
                    {
                        "pageName": "HSC_YOUTH_SERVICES",
                        "condition": ":dataitem.HSC_FIRE_SAFETY_TUTORING$HSA_FIRE_SAFETY_TUTORING.HSC$YESNO = 'true'"
                    },
                    {
                        "pageName": "HSC_OPS_INFO",
                        "condition": ""
                    }
                ]
            },
            {
                "name": "HSC_YOUTH_SERVICES",
                "title": "Youth Services",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Youth Services",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_YOUTH_SERVICES_CONSENT.HSC",
                        "label": "Does the parent/guardian consent to being contacted by Youth Services?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "YOUTH_SERVICES_REASON.HSC",
                        "label": "Reason",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Fear of fire",
                                "value": "86467"
                            },
                            {
                                "label": "Firesafe",
                                "value": "86466"
                            },
                            {
                                "label": "Firesetters",
                                "value": "86465"
                            },
                            {
                                "label": "Xtinguish",
                                "value": "86464"
                            },
                            {
                                "label": "Nightrider",
                                "value": "86463"
                            }
                        ],
                        "attributes": {
                            "multiselect": "true",
                            "type_id": "86470",
                            "required": "true",
                            "condition": ":dataitem.HSC_YOUTH_SERVICES$HSA_YOUTH_SERVICES_CONSENT.HSC$YESNO = 'true'"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_OPS_INFO",
                "title": "Additional Risks",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Additional Risks",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "ADDN_RISKS",
                        "label": "Are any of the following hazards present which may have an impact at an incident?",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Oxygen Used/Stored",
                                "value": "150022"
                            },
                            {
                                "label": "Unguarded open fires in use",
                                "value": "150023"
                            },
                            {
                                "label": "Excessive or dangerous storage",
                                "value": "85958"
                            },
                            {
                                "label": "Missing Internal Doors",
                                "value": "85959"
                            },
                            {
                                "label": "Portable heaters (LPG/Paraffin)",
                                "value": "85960"
                            },
                            {
                                "label": "Dangerous Animals/Pets",
                                "value": "85961"
                            }
                        ],
                        "attributes": {
                            "multiselect": "true",
                            "type_id": "85957",
                            "required": "true"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_OPS_INFO_NOTES",
                "title": "Additional Risk Notes",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Risks: Additional Information",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_ADDITIONAL_RISK_NOTES.HSC",
                        "label": "Please provide details",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_SAFETY_ADVICE",
                "title": "Safety Advice",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Safety Advice/Planning",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "SAD",
                        "label": "Was the following discussed?",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Night time routine",
                                "value": "85947"
                            },
                            {
                                "label": "Escape plan",
                                "value": "85948"
                            },
                            {
                                "label": "Smoke alarm testing",
                                "value": "85949"
                            },
                            {
                                "label": "Kitchen safety",
                                "value": "85950"
                            },
                            {
                                "label": "Electrical safety",
                                "value": "85951"
                            },
                            {
                                "label": "Candle safety",
                                "value": "85952"
                            },
                            {
                                "label": "Smoking safety",
                                "value": "85953"
                            },
                            {
                                "label": "Calling the Fire Service",
                                "value": "85954"
                            }
                        ],
                        "attributes": {
                            "multiselect": "true",
                            "type_id": "85946"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_BEFORE_EQUIPMENT",
                "title": "Fire Protection Equipment",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Fire Protection Equipment",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "label_1",
                        "label": "Before this HSC",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_BEFORE_CORRECT_ALARMS.HSC",
                        "label": "How many smoke alarms were working and correctly sited?",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": {
                            "required": "true"
                        }
                    },
                    {
                        "name": "HSA_BEFORE_INCORRECT_ALARMS.HSC",
                        "label": "How many smoke alarms were not correctly fitted or not sited correctly?",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": {
                            "required": "true"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_EQUIPMENT_FITTED",
                "title": "Fire Protection Equipment",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Fire Protection Equipment",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "label_1",
                        "label": "As a result of this HSC...",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HEQ_TYPE_ID.HSC",
                        "label": "Equipment fitted or to be fitted",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Smoke Alarm",
                                "value": "85971"
                            },
                            {
                                "label": "Hearing Impaired Alarm",
                                "value": "85972"
                            },
                            {
                                "label": "Fire Guards",
                                "value": "85973"
                            },
                            {
                                "label": "Double Electric Blanket",
                                "value": "85975"
                            },
                            {
                                "label": "Single Electric Blanket",
                                "value": "85976"
                            },
                            {
                                "label": "Deep Fat Fryer",
                                "value": "85977"
                            },
                            {
                                "label": "Mailbox protection system flexible fire bag",
                                "value": "85978"
                            },
                            {
                                "label": "Extension Leads",
                                "value": "150008"
                            },
                            {
                                "label": "Spark Guards",
                                "value": "150009"
                            },
                            {
                                "label": "Letterbox blanking plate",
                                "value": "150010"
                            },
                            {
                                "label": "Letterbox lockable catch",
                                "value": "150011"
                            }
                        ],
                        "attributes": null
                    },
                    {
                        "name": "HEQ_NUMBER_FITTED.HSC",
                        "label": "Number fitted",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HEQ_MAKE_MODEL_ID.HSC",
                        "label": "Make",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Fire Angel",
                                "value": "85982"
                            },
                            {
                                "label": "Not Applicable",
                                "value": "85983"
                            }
                        ],
                        "attributes": null
                    },
                    {
                        "name": "HEQ_BATCH_NO.HSC",
                        "label": "Batch No",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_EDUCATIONAL_MATERIAL",
                "title": "Safety Advice",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Educational Material",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_EDUCATIONAL_MATERIAL.HSC",
                        "label": "Was educational material supplied?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_ASK_CARBON_MONOXIDE",
                "title": "Carbon Monoxide Awareness",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Carbon Monoxide Awareness",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSC_ASK_CARBON_MONOXIDE.HSC",
                        "label": "Would you like to add or review Carbon Monoxide questions?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": [
                    {
                        "pageName": "HSC_CARBON_MONOXIDE",
                        "condition": ":dataitem.HSC_ASK_CARBON_MONOXIDE$HSC_ASK_CARBON_MONOXIDE.HSC$YESNO = 'true'"
                    },
                    {
                        "pageName": "HSC_VPO",
                        "condition": ""
                    }
                ]
            },
            {
                "name": "HSC_CARBON_MONOXIDE",
                "title": "Carbon Monoxide",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Carbon Monoxide",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "CO_READING.HSC",
                        "label": "Carbon Monoxide reading",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "CO_HEATING_METHOD.HSC",
                        "label": "Primary heating method",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Electric",
                                "value": "86070"
                            },
                            {
                                "label": "Gas",
                                "value": "86071"
                            },
                            {
                                "label": "Oil",
                                "value": "86072"
                            },
                            {
                                "label": "Wood Burner",
                                "value": "86073"
                            },
                            {
                                "label": "Coal",
                                "value": "86074"
                            },
                            {
                                "label": "Other",
                                "value": "86075"
                            }
                        ],
                        "attributes": null
                    },
                    {
                        "name": "CO_COOKING_METHOD.HSC",
                        "label": "Primary cooking method",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Electric",
                                "value": "86070"
                            },
                            {
                                "label": "Gas",
                                "value": "86071"
                            },
                            {
                                "label": "Oil",
                                "value": "86072"
                            },
                            {
                                "label": "Wood Burner",
                                "value": "86073"
                            },
                            {
                                "label": "Coal",
                                "value": "86074"
                            },
                            {
                                "label": "Other",
                                "value": "86075"
                            }
                        ],
                        "attributes": null
                    },
                    {
                        "name": "CO_APPLIANCES_ON.HSC",
                        "label": "Were there any cooking or heating appliances on at the time of visit?",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Yes",
                                "value": "3432"
                            },
                            {
                                "label": "No",
                                "value": "3433"
                            },
                            {
                                "label": "Unknown",
                                "value": "3434"
                            }
                        ],
                        "attributes": null
                    },
                    {
                        "name": "label_1",
                        "label": "Gas Certificate",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "CO_GAS_CERTIFICATE.HSC",
                        "label": "Gas Appliances; have the occupiers got a gas safety certificate",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Yes",
                                "value": "3432"
                            },
                            {
                                "label": "No",
                                "value": "3433"
                            },
                            {
                                "label": "Unknown",
                                "value": "3434"
                            }
                        ],
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_VPO",
                "title": "Vulnerable Persons",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Vulnerable Persons",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "HSA_VULNERABLE_PERSON.HSC",
                        "label": "Vulnerable person - additional actions required? (please specify)",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "VPO_REFERRALS.HSC",
                        "label": "Check all that apply",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "MARAC",
                                "value": "86490"
                            },
                            {
                                "label": "Staffordshire Police",
                                "value": "86489"
                            },
                            {
                                "label": "NHS / PCT",
                                "value": "86488"
                            },
                            {
                                "label": "Stoke City Council",
                                "value": "86487"
                            },
                            {
                                "label": "County Council",
                                "value": "86486"
                            },
                            {
                                "label": "Carecall/Telecare",
                                "value": "86485"
                            },
                            {
                                "label": "Social Services",
                                "value": "86484"
                            },
                            {
                                "label": "Disability Solutions",
                                "value": "86483"
                            },
                            {
                                "label": "Housing Association",
                                "value": "86482"
                            },
                            {
                                "label": "Smoking Cessation",
                                "value": "86481"
                            },
                            {
                                "label": "DWP",
                                "value": "86480"
                            },
                            {
                                "label": "Age UK",
                                "value": "86479"
                            },
                            {
                                "label": "Victim Support",
                                "value": "86478"
                            },
                            {
                                "label": "SSAFA",
                                "value": "86477"
                            },
                            {
                                "label": "ADSIS DAT",
                                "value": "86476"
                            },
                            {
                                "label": "Vulnerable Person",
                                "value": "86473"
                            },
                            {
                                "label": "Interpretor",
                                "value": "86472"
                            },
                            {
                                "label": "Sign Language",
                                "value": "86471"
                            },
                            {
                                "label": "Hoarding / Housing issues",
                                "value": "86474"
                            }
                        ],
                        "attributes": {
                            "multiselect": "true",
                            "type_id": "86470",
                            "required": "true",
                            "condition": ":dataitem.HSC_VPO$HSA_VULNERABLE_PERSON.HSC$YESNO = 'true'"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "HSC_ASSESSMENT_NOTES",
                "title": "HFRC Notes",
                "items": [
                    {
                        "name": "label_0",
                        "label": "Notes",
                        "type": "TEXT",
                        "value": "Please provide any further information that may be useful in respect to this assessment, including any follow up actions that may be necessary.",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    },
                    {
                        "name": "HSA_ASSESSMENT_NOTES.HSC",
                        "label": "",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            }
        ]
    },
    {
        "id": 2,
        "name": "three_page",
        "description": "Three Page Test",
        "pages": [
            {
                "name": "page1",
                "title": "Page 1",
                "items": [
                    {
                        "name": "label",
                        "label": "This is page 1. The next page should be page 3.",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": [
                    {
                        "pageName": "page3",
                        "condition": ""
                    }
                ]
            },
            {
                "name": "page2",
                "title": "Page 3",
                "items": [
                    {
                        "name": "label",
                        "label": "This is page 2. You should never see this page.",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "page3",
                "title": "Page 3",
                "items": [
                    {
                        "name": "label",
                        "label": "This is page 3. The previous page should be page 1.",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            }
        ]
    },
    {
        "id": 3,
        "name": "hello_world",
        "description": "Hello world example",
        "pages": [
            {
                "name": "page1",
                "title": "Page 1",
                "items": [
                    {
                        "name": "label",
                        "label": "Hello World!",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            }
        ]
    },
    {
        "id": 4,
        "name": "hello_name",
        "description": "Another Hello world example",
        "pages": [
            {
                "name": "page1",
                "title": "Hello",
                "items": [
                    {
                        "name": "label",
                        "label": "Howdy",
                        "type": "TEXT",
                        "value": "Stranger",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    }
                ],
                "nextPages": null
            }
        ]
    },
    {
        "id": 63,
        "name": "hsc_assessment",
        "description": "HSC Assessment",
        "pages": [
            {
                "name": "intro",
                "title": "Information",
                "items": [
                    {
                        "name": "contact",
                        "label": "Contact",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    },
                    {
                        "name": "address",
                        "label": "Address",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    },
                    {
                        "name": "password",
                        "label": "Password",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    },
                    {
                        "name": "notes",
                        "label": "Notes",
                        "type": "TEXT",
                        "value": "",
                        "values": null,
                        "attributes": {
                            "readonly": "true"
                        }
                    }
                ],
                "nextPages": null
            },
            {
                "name": "assessment_details",
                "title": "Assessment Details",
                "items": [
                    {
                        "name": "assessor_1",
                        "label": "Assessor 1",
                        "type": "TEXT",
                        "value": "current_user",
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "assessor_2",
                        "label": "Assessor 2",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "share_details",
                "title": "Share Information",
                "items": [
                    {
                        "name": "share_label",
                        "label": "Explain to the occupier how the information gathered during this assessment can be shared with partner organisations",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "share_info",
                        "label": "Occupier aggrees to share?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "property_details",
                "title": "Property Details",
                "items": [
                    {
                        "name": "tenure_type",
                        "label": "Tenure Type",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "Owner occupied",
                                "value": "owner_occupied"
                            },
                            {
                                "label": "Rented - Council/Housing Association",
                                "value": "rented_ha"
                            },
                            {
                                "label": "Rented - Private",
                                "value": "rented_private"
                            },
                            {
                                "label": "Rented - Landlord unknown",
                                "value": "rented_unknown"
                            },
                            {
                                "label": "Not known",
                                "value": "not_known"
                            }
                        ],
                        "attributes": null
                    },
                    {
                        "name": "ha_id",
                        "label": "Housing Association",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "hmo",
                        "label": "HMO?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "occupancy",
                "title": "Occupancy",
                "items": [
                    {
                        "name": "ethnicity",
                        "label": "Ethnicity",
                        "type": "SELECT",
                        "value": null,
                        "values": [
                            {
                                "label": "British",
                                "value": "white_british"
                            },
                            {
                                "label": "Irish",
                                "value": "white_irish"
                            },
                            {
                                "label": "Gypsy or Irish Traveller",
                                "value": "white_traveller"
                            },
                            {
                                "label": "Any other White background",
                                "value": "white_other"
                            },
                            {
                                "label": "White & Black Caribbean",
                                "value": "mixed_caribbean"
                            },
                            {
                                "label": "White & Black African",
                                "value": "mixed_african"
                            },
                            {
                                "label": "White & Asian",
                                "value": "mixed_asian"
                            },
                            {
                                "label": "Other Mixed",
                                "value": "mixed_other"
                            },
                            {
                                "label": "Indian",
                                "value": "asian_indian"
                            },
                            {
                                "label": "Pakistani",
                                "value": "asian_pakistani"
                            },
                            {
                                "label": "Bangladeshi",
                                "value": "asian_bangladeshi"
                            },
                            {
                                "label": "Any other Asian background",
                                "value": "asian_other"
                            },
                            {
                                "label": "African",
                                "value": "black_african"
                            },
                            {
                                "label": "Caribbean",
                                "value": "black_caribbean"
                            },
                            {
                                "label": "Any other Black background",
                                "value": "black_other"
                            },
                            {
                                "label": "Arab",
                                "value": "other_arab"
                            },
                            {
                                "label": "Any other ethnic background",
                                "value": "other_any"
                            }
                        ],
                        "attributes": {
                            "multiselect": "true"
                        }
                    },
                    {
                        "name": "occupancy_numbers",
                        "label": "Occupancy Numbers",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "occupants_0-5",
                        "label": "0-5",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "occupants_65+",
                        "label": "65+",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "occupants_other",
                        "label": "Other",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "fire_hazards",
                "title": "Fire Hazards",
                "items": [
                    {
                        "name": "hazard_chippan",
                        "label": "Is a chip pan in use?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "hazard_blankets",
                        "label": "Electric blankets in the property?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "hazard_smoker",
                        "label": "Does anyone in the household smoke?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "hazard_candles",
                        "label": "Are candles used inappropriately?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "hazard_clothes",
                        "label": "Are clothes dried dangerously?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "hazard_sockets",
                        "label": "Are plug sockets/adaptors overloaded?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "hazard_notes",
                "title": "Fire Hazard Notes",
                "items": [
                    {
                        "name": "fire_hazard_label",
                        "label": "Please provide any further information relating to the fire hazards.",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "fire_hazard_notes",
                        "label": "Further information",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "individual_risks",
                "title": "Individual Risks",
                "items": [
                    {
                        "name": "mobility_label",
                        "label": "Will the occupier...",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "slow_to_vacate",
                        "label": "Be slow to vacate the property?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "assistance_to_vacate",
                        "label": "Require assistance to vacate the property?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "techrescue_vacate",
                        "label": "Require specialist tech rescue equipment?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "impairment_label",
                        "label": "Does the occupier have...",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "visual_impairment",
                        "label": "a Visual impairment?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "hearing_impairment",
                        "label": "a Hearing impairment?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "other_impairment",
                        "label": "any Other impairment?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "other_impairment_details",
                        "label": "Details",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "firesafety_tutoring",
                "title": "Fire Safety Tutoring",
                "items": [
                    {
                        "name": "history_of_fire",
                        "label": "Is there evidence/history of fire?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "tutoring_required",
                        "label": "Is fire safety tutoring required?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "youth_services_consent",
                        "label": "Do the parent/guardian consent to being contacted by Youth Services?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "youthservices_notes",
                        "label": "Details",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "additional_risks",
                "title": "Additional Operational Risks",
                "items": [
                    {
                        "name": "dangerous_storage",
                        "label": "Excessive or Dangerous storage?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "missing_doors",
                        "label": "Missing internal doors?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "portable_heating",
                        "label": "Portable heating (LPG/paraffin)?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "dangerous_animals",
                        "label": "Dangerous Animals/Pets?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "medical_oxygen",
                        "label": "Medical Oxygen?",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "additional_risks_notes",
                        "label": "Notes",
                        "type": "TEXT",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "safety_advice",
                "title": "Safety Advice/Planning",
                "items": [
                    {
                        "name": "advice_night",
                        "label": "Night time routine discussion",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "advice_escape_plan",
                        "label": "Escape Plan discussion",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "advice_alarm_test",
                        "label": "Smoke alarm tested",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "advice_kitchen",
                        "label": "Kitchen safety",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "advice_electrical",
                        "label": "Electrical safety",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "advice_candle",
                        "label": "Candle safety",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "advice_smoking",
                        "label": "Smoking safety",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "advice_calling_fire",
                        "label": "Calling the Fire Service",
                        "type": "YESNO",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            },
            {
                "name": "protection_equipment",
                "title": "Fire Protection Equipment",
                "items": [
                    {
                        "name": "equipment_before_label",
                        "label": "Before assessment",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "alarms_correct_before",
                        "label": "How many smoke alarms were working AND correctly sited?",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "alarms_incorrect_before",
                        "label": "How many smoke alarms were not working OR not sited correctly?",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "equipment_after_label",
                        "label": "After assessment",
                        "type": "LABEL",
                        "value": null,
                        "values": null,
                        "attributes": null
                    },
                    {
                        "name": "alarms_correct_after",
                        "label": "How many smoke alarms were fitted?",
                        "type": "NUMERIC",
                        "value": null,
                        "values": null,
                        "attributes": null
                    }
                ],
                "nextPages": null
            }
        ]
    }

]