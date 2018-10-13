## Table of Contents

1. [AddNewProblem](#AddNewProblem)
2. [ViewProblems](#ViewProblems)
3. [EditProblem](#EditProblem)
4. [DeleteProblem](#DeleteProblem)
5. [AddNewRecord](#AddNewRecord)
6. [ViewRecordsAssociatedWithProblem](#ViewRecordsAssociatedWithProblem)
7. [MakeSlideshowOfPhotoRecordsOfProblem](#MakeSlideshowOfPhotoRecordsOfProblem)
8. [AddNewUserAccount](#AddNewUserAccount)
9. [EditContactInformation](#EditContactInformation)
10. [ShowContactInformationForUser](#ShowContactInformationForUser)
11. [FetchProblemsAndRecordsWithAllKeywords](#FetchProblemsAndRecordsWithAllKeywords)
12. [FetchProblemsAndRecordsWithKeyWordsAndGeoLocatioin](#FetchProblemsAndRecordsWithKeyWordsAndGeoLocatioin)
13. [ViewAssignedPatients](#ViewAssignedPatients)
14. [AddNewPatient](#AddNewPatient)
15. [ViewProblemsForPatient](#ViewProblemsForPatient)
16. [AddCommentToRecord](#AddCommentToRecord)
17. [ViewGeoLocationForRecord](#ViewGeoLocationForRecord)
18. [ViewGeoLocationForAllRecords](#ViewGeoLocationForAllRecords)
19. [UploadBodyLocationPhotos](#UploadBodyLocationPhotos)
20. [ViewBodyLocationOfRecord](#ViewBodyLocationOfRecord)

### AddNewProblem <a name="AddNewProblem"></a>
| Use Case 1           | AddNewProblem                                                                                                                          |
|----------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 01.01.01, US 01.01.02, US 01.01.03, US 08.01.01                                                                                     |
| Participating Actors | User                                                                                                                                   |
| Goal                 | User adds a new problem                                                                                                                |
| Trigger              | User chooses the new problem option                                                                                                    |
| Precondition         | 1. User has a title for the problem                                                                                                    |
|                      | 2. User has a description for the problem                                                                                              |
|                      | 3. User has a Start date for the problem                                                                                               |
|                      | 4. User is a patient                                                                                                                   |
| Postcondition        | On success:                                                                                                                            |
|                      | 1. A new problem has been created                                                                                                      |
|                      | 2. A message indicating success is displayed.                                                                                          |
| Basic Flow           | 1. System prompts the user to enter a title, date started, and a brief description on a single screen                                  |
|                      | 2. User enters the title                                                                                                               |
|                      | 3. User taps the start date                                                                                                            |
|                      | 4. User selects a start date from the calendar shown                                                                                   |
|                      | 5. User enters a brief description                                                                                                     |
|                      | 6. User chooses to save the problem                                                                                                    |
|                      | 7. System saves the problem                                                                                                            |
|                      | 8. System displays success message                                                                                                     |
| Exceptions           | 6. The information the User has entered has violated a constraint                                                                      |
|                      | 6.1 System displays an error message to the User, telling the User the constraint that was violated                                    |
|                      | 6.2 System prompts the User to fix the violated constraint                                                                             |
| Qualities            | System is fast, responsive and reactive. If system is offline, the problem is saved locally and then synced when the system is online. |
| Constraints          | 2.1 Problem title is required                                                                                                          |
|                      | 2.2 Problem title is unique                                                                                                            |
|                      | 2.3 Problem title must be no more than 30 characters                                                                                   |
|                      | 4.1 Problem's start date is required                                                                                                   |
|                      | 5.1 Problem description is required                                                                                                    |
|                      | 5.2 Problem description must be no more than 300 characters                                                                            |
| Includes             |                                                                                                                                        |
| Extends              |                                                                                                                                        |
| Related Artifacts    |                                                                                                                                        |
| Notes                |                                                                                                                                        |
| Open Issues          |                                                                                                                                        |

### ViewProblems <a name="ViewProblems"></a>
| Use Case 2           | ViewProblems                                                                                         |
|----------------------|------------------------------------------------------------------------------------------------------|
| Related User Story   | US 01.02.01                                                                                          |
| Participating Actors | User                                                                                                 |
| Goal                 | User views the problems the user has recorded                                                        |
| Trigger              | User chooses the view problems action                                                                |
| Precondition         | 1. User is a patient                                                                                 |
| Postcondition        | On success:                                                                                          |
|                      | 1. A list of the user's problems is displayed                                                        |
| Basic Flow           | 1. The system displays user's problems                                                               |
| Exceptions           | 1 User has no problems recorded                                                                      |
|                      | 1.1 System displays a message for the user indicating that the user has not recorded any problems.   |
| Qualities            | System is fast, responsive and reactive. If system is offline, the locally saved problems are shown. |
| Constraints          |                                                                                                      |
| Includes             |                                                                                                      |
| Extends              |                                                                                                      |
| Related Artifacts    |                                                                                                      |
| Notes                |                                                                                                      |
| Open Issues          |                                                                                                      |

### EditProblem <a name="EditProblem"></a>
| Use Case 3           | EditProblem                                                                                                                             |
|----------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 01.01.02, US 01.01.03, US 01.03.01, US 08.01.01                                                                                      |
| Participating Actors | User                                                                                                                                    |
| Goal                 | User edits a problem                                                                                                                    |
| Trigger              | User chooses the edit problem option                                                                                                    |
| Precondition         | 1. User knows the problem to be edited                                                                                                  |
|                      | 2. Problem to be edited has already been recorded                                                                                       |
|                      | 3. User is a patient                                                                                                                    |
| Postcondition        | On success:                                                                                                                             |
|                      | 1. The problem has been edited and stored                                                                                               |
|                      | 2. A message indicating success is displayed.                                                                                           |
| Basic Flow           | 1. System displays the problem to be edited with a title, date started, and a brief description on a single screen                      |
|                      | 2. User optionally edits title                                                                                                          |
|                      | 3. User optionally taps the start date                                                                                                  |
|                      | 4. User optionally edits the start date from the calendar shown                                                                         |
|                      | 5. User optionally edits brief description                                                                                              |
|                      | 6. User chooses to save the changes to the problem                                                                                      |
|                      | 7. System saves the problem                                                                                                             |
|                      | 8. System displays success message                                                                                                      |
| Exceptions           | 6. The information the User has entered has violated a constraint                                                                       |
|                      | 6.1 System displays an error message to the User, telling User the constraint that was violated                                         |
|                      | 6.2 System prompts the User to fix the violated constraint                                                                              |
| Qualities            | System is fast, responsive and reactive. If system is offline, the problems is saved locally and then synced when the system is online. |
| Constraints          | 2.1 Problem title is required                                                                                                           |
|                      | 2.2 Problem title is unique                                                                                                             |
|                      | 2.3 Problem title must be no more than 30 characters                                                                                    |
|                      | 4.1 Problem’s start date is required                                                                                                    |
|                      | 5.1 Problem description is required                                                                                                     |
|                      | 5.2 Problem description must be no more than 300 characters                                                                             |
| Includes             |                                                                                                                                         |
| Extends              |                                                                                                                                         |
| Related Artifacts    |                                                                                                                                         |
| Notes                |                                                                                                                                         |
| Open Issues          |                                                                                                                                         |

### DeleteProblem <a name="DeleteProblem"></a>
| Use Case 4           |                                                                                                                                        |
|----------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 01.04.01, US 08.01.01                                                                                                               |
| Participating Actors | User                                                                                                                                   |
| Goal                 | Delete a user’s problem                                                                                                                |
| Trigger              | User chooses the delete problem option                                                                                                 |
| Precondition         | 1. User knows the problem to be deleted                                                                                                |
|                      | 2. Problem to be deleted has already been recorded                                                                                     |
|                      | 3. User is a patient                                                                                                                   |
| Postcondition        | On success:                                                                                                                            |
|                      | 1. The problem has been deleted                                                                                                        |
|                      | 2. Any records associated with the problem have been deleted                                                                           |
|                      | 2. A message indicating success is displayed.                                                                                          |
| Basic Flow           | 1. System warns user that the problem and all associated records will be deleted.                                                      |
|                      | 2. System prompts the user to continue or cancel                                                                                       |
|                      | 3. System deletes all the records associated with the problem                                                                          |
|                      | 4. System deletes the problem                                                                                                          |
|                      | 5. System displays success message                                                                                                     |
| Exceptions           | 2 User cancels deletion of problem                                                                                                     |
|                      | 2.1 System returns to previous screen                                                                                                  |
| Qualities            | System is fast, responsive and reactive. If system is offline, the problem is saved locally and then synced when the system is online. |
| Constraints          |                                                                                                                                        |
| Includes             |                                                                                                                                        |
| Extends              |                                                                                                                                        |
| Related Artifacts    |                                                                                                                                        |
| Notes                |                                                                                                                                        |
| Open Issues          |                                                                                                                                        |

### AddNewRecord <a name="AddNewRecord"></a>
| Use Case 5           | AddNewRecord                                                                                                                                                                         |
|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 01.05.01, US 02.02.01, US 02.03.01, US 02.04.01, US 02.05.01, US 02.06.01, US 02.08.01, US 02.09.01, US 08.01.01, US 09.01.01, US 09.01.02, US 09.03.01, US 10.01.01, US 11.02.01 |
| Participating Actors | User, Sys Admin                                                                                                                                                                      |
| Goal                 | Create a new record associated with the desired problem.                                                                                                                             |
| Trigger              | User chooses the add new record option.                                                                                                                                              |
| Precondition         | 1. User knows the problem for which the user wants to add a new record                                                                                                               |
|                      | 2. User has previously recorded the problem for which the user wants to add the new record for                                                                                       |
|                      | 3. User is a patient                                                                                                                                                                 |
| Postcondition        | On success:                                                                                                                                                                          |
|                      | 1. A new record has been added with an associated problem.                                                                                                                           |
|                      | 2. A message indicating success is displayed.                                                                                                                                        |
| Basic Flow           | 1. System prompts the user to select a problem to associated with the record with                                                                                                    |
|                      | 2. User selects the problem                                                                                                                                                          |
|                      | 3. System adds a time stamp to the record                                                                                                                                            |
|                      | 4. System reminds user to take photos in similar conditions as the other photos.                                                                                                     |
|                      | 5. System prompts the user to add a geo-location, body location, title, comment and photos to the record.                                                                            |
|                      | 6. User optionally enters a title                                                                                                                                                    |
|                      | 7. User optionally enters a comment                                                                                                                                                  |
|                      | 8. User optionally specifies a body location using a point location on the front/back body location photos                                                                           |
|                      | 9. User optionally geo-codes the record using a map                                                                                                                                  |
|                      | 10. User optionally attaches one or more photos of the problem                                                                                                                       |
|                      | 11. User saves the record                                                                                                                                                            |
|                      | 12. System creates the record                                                                                                                                                        |
|                      | 13. System displays a success message                                                                                                                                                |
| Exceptions           | 9. Location data for the app has not been enabled                                                                                                                                    |
|                      | 9.1 System displays error message stating that it failed to add location data to the record.                                                                                         |
|                      | 10a. System cannot access the photo directory on the device                                                                                                                          |
|                      | 10a.1 System displays an error message stating that it failed to attach the photo(s) to the record.                                                                                  |
|                      | 10b. User attaches more than 10 photos                                                                                                                                               |
|                      | 10b.1 System displays an error message stating that too many images have been added to the record. Only 10 images at most can be attached to a record                                |
|                      | 10b.2 System prompts the user to fix the issue                                                                                                                                       |
|                      | 10c. User attaches a photo larger than 65546 bytes                                                                                                                                   |
|                      | 10c.1 System displays an error message stating that the size of a given photo is too large                                                                                           |
|                      | 10c.2 System prompts the user to fix the issue                                                                                                                                       |
| Qualities            | System is fast, responsive and reactive. If system is offline, the record is saved locally and then synced when the system is online.                                                |
| Constraints          |                                                                                                                                                                                      |
| Includes             |                                                                                                                                                                                      |
| Extends              |                                                                                                                                                                                      |
| Related Artifacts    |                                                                                                                                                                                      |
| Notes                |                                                                                                                                                                                      |
| Open Issues          |                                                                                                                                                                                      |

### ViewRecordsAssociatedWithProblem <a name="ViewRecordsAssociatedWithProblem"></a>
| Use Case 6           | ViewRecordsAssociatedWithProblem                                                                                                                                                                 |
|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 02.01.01, US 08.01.01, US 09.02.01                                                                                                                                                            |
| Participating Actors | User                                                                                                                                                                                             |
| Goal                 | User sees the records associated with a problem                                                                                                                                                  |
| Trigger              | User chooses the view records for a problem action                                                                                                                                               |
| Precondition         | 1. User knows the problem for which the user wants to view the associated records                                                                                                                |
|                      | 2. A User has recorded the problem and records in the past                                                                                                                                       |
|                      | 3. If user is care-provider, ensure the user is assigned to the patient and the user is online.                                                                                                  |
|                      | 4. If a user is patient, no other preconditions need to be fulfilled                                                                                                                             |
| Postcondition        | On success:                                                                                                                                                                                      |
|                      | 1. System displays all the records for a given problem with the record's title, description and photos.                                                                                          |
| Basic Flow           | 1. System displays records for a given problem with the record's title, description and photos.                                                                                                  |
| Exceptions           | 1a. User has no problems recorded                                                                                                                                                                |
|                      | 1a.1 System displays a message indicating that a user has no problems recorded                                                                                                                   |
|                      | 1b. User is care-provider and is not assigned to the patient                                                                                                                                     |
|                      | 1b.1 System displays a message indicating that the user is a care-provider and is not assigned to the patient                                                                                    |
| Qualities            | System is fast, responsive and reactive. If system is offline, the locally saved records are shown if the user is a patient. If the user is a care-provider and offline, error message is shown. |
| Constraints          |                                                                                                                                                                                                  |
| Includes             |                                                                                                                                                                                                  |
| Extends              |                                                                                                                                                                                                  |
| Related Artifacts    |                                                                                                                                                                                                  |
| Notes                |                                                                                                                                                                                                  |
| Open Issues          |                                                                                                                                                                                                  |

### MakeSlideshowOfPhotoRecordsOfProblem <a name="MakeSlideshowOfPhotoRecordsOfProblem"></a>
| Use Case 7           | MakeSlideshowOfPhotoRecordsOfProblem                                                                |
|----------------------|-----------------------------------------------------------------------------------------------------|
| Related User Story   | US 02.10.01, US 08.01.01                                                                            |
| Participating Actors | User                                                                                                |
| Goal                 | User sees a slideshow of the photo records of a problem.                                            |
| Trigger              | User chooses the view a slideshow of the photo records of a problem action.                         |
| Precondition         | 1. User knows the problem for which the user wants to view photo records of                         |
|                      | 2. User has recorded the problem and records for the problem in the past                            |
|                      | 3. User is patient                                                                                  |
| Postcondition        | On success:                                                                                         |
|                      | 1. System displays all the photo records of a given problem as a slideshow                          |
| Basic Flow           | 1. System displays photo records for a given problem in chronological order                         |
| Exceptions           | 1a. User has no problems                                                                            |
|                      | 1a.1 System displays a message indicating that the user has no problems recorded                    |
|                      | 1b. User has no photo records for tye problem                                                       |
|                      | 1b.1 System displays a message indicating that a user has no photo records for the problem          |
| Qualities            | System is fast, responsive and reactive. If system is offline, the locally saved records are shown. |
| Constraints          |                                                                                                     |
| Includes             |                                                                                                     |
| Extends              |                                                                                                     |
| Related Artifacts    |                                                                                                     |
| Notes                |                                                                                                     |
| Open Issues          |                                                                                                     |

### AddNewUserAccount <a name="AddNewUserAccount"></a>
| Use Case 8           | AddNewUserAccount                                                                                                                                                         |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 03.01.01, US 03.01.02, US 03.01.03                                                                                                                                     |
| Participating Actors | User                                                                                                                                                                      |
| Goal                 | User creates a new account                                                                                                                                                |
| Trigger              | User chooses the AddNewUserAccount(sign-up) option                                                                                                                        |
| Precondition         | 1. User must be online                                                                                                                                                    |
|                      | 2. User must know the unique userid the user wants to use                                                                                                                 |
|                      | 3. User must know his/her contact information                                                                                                                             |
|                      | 4. User must know if he/she is a patient or care-provider                                                                                                                 |
| Postcondition        | On success:                                                                                                                                                               |
|                      | 1. New user account for the user has been created                                                                                                                         |
|                      | 2. System displays a message indicating success                                                                                                                           |
| Basic Flow           | 1. System prompts the user to enter a unique userid, email address and phone number                                                                                       |
|                      | 2. User enters a unique userid                                                                                                                                            |
|                      | 3. User selects if he/she is a care-provider or patient                                                                                                                   |
|                      | 4. User enters an email address                                                                                                                                           |
|                      | 5. User enters a phone number                                                                                                                                             |
|                      | 6. User selects the action to create account                                                                                                                              |
|                      | 7. System creates a new account                                                                                                                                           |
|                      | 8. System displays success message                                                                                                                                        |
| Exceptions           | 2. User’s userid is not unique                                                                                                                                            |
|                      | 2.1 System displays an error message indicating the userid is not unique                                                                                                  |
|                      | 2.2 System prompts the user to fix the error                                                                                                                              |
|                      | 2 User’s userid is too short                                                                                                                                              |
|                      | 2.1 System displays an error message indicating the userid is must be 8 characters or longer                                                                              |
|                      | 2.2 System prompts the user to fix the error                                                                                                                              |
|                      | 3 User has not selected if the user is a care provider or patient                                                                                                         |
|                      | 3.1 System displays an error message indicating the user must select if he/she is a care-provider or patient                                                              |
|                      | 3.2 System prompts the user to fix the error                                                                                                                              |
| Qualities            | System is fast, responsive and reactive.                                                                                                                                  |
| Constraints          | 1. User id must be unique                                                                                                                                                 |
|                      | 2. User id must be 8 characters or longer                                                                                                                                 |
|                      | 3. User must specify if he/she is a patient or a care-provider                                                                                                            |
| Includes             |                                                                                                                                                                           |
| Extends              |                                                                                                                                                                           |
| Related Artifacts    |                                                                                                                                                                           |
| Notes                | Since no user stories for the creation of accounts for care-providers have been given, the requirements for patient’s user profiles have been extended to care-providers. |
| Open Issues          |                                                                                                                                                                           |

### EditContactInformation <a name="EditContactInformation"></a>
| Use Case 9           | EditContactInformation                                                                                                                                                    |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 03.02.01                                                                                                                                                               |
| Participating Actors | User                                                                                                                                                                      |
| Goal                 | Change the contact information for a user's profile.                                                                                                                      |
| Trigger              | User chooses the edit contact information action                                                                                                                          |
| Precondition         | 1. User must be online                                                                                                                                                    |
|                      | 2. User must know their new contact information                                                                                                                           |
| Postcondition        | On success:                                                                                                                                                               |
|                      | 1. System diaplays a message indicating that the user’s contact information has been successfully changed                                                                 |
| Basic Flow           | 1. System prompts the user to edit their email address or edit their phone number.                                                                                        |
|                      | 2. User optionally edits their email address                                                                                                                              |
|                      | 3. User optionally edits their phone number                                                                                                                               |
|                      | 4. User choose the save the new contact information action                                                                                                                |
|                      | 5. System updates the contact information                                                                                                                                 |
| Exceptions           |                                                                                                                                                                           |
| Qualities            | System is fast, responsive and reactive.                                                                                                                                  |
| Constraints          |                                                                                                                                                                           |
| Includes             |                                                                                                                                                                           |
| Extends              |                                                                                                                                                                           |
| Related Artifacts    |                                                                                                                                                                           |
| Notes                | Since no user stories for the creation of accounts for care-providers have been given, the requirements for patient’s user profiles have been extended to care-providers. |
| Open Issues          |                                                                                                                                                                           |

### ShowContactInformationForUser <a name="ShowContactInformationForUser"></a>
| Use Case 10          | ShowContactInformationForUser                                                                                                                                             |
|----------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 03.03.01                                                                                                                                                               |
| Participating Actors | User                                                                                                                                                                      |
| Goal                 | User sees the contact information associated with a username                                                                                                              |
| Trigger              | User chooses the view contact information associated with a username action                                                                                               |
| Precondition         | 1. User must be online                                                                                                                                                    |
|                      | 2. User must know which user’s contact information he/she wants to view                                                                                                   |
| Postcondition        | On success:                                                                                                                                                               |
|                      | 1. The system displays the desired user’s contact information                                                                                                             |
| Basic Flow           | 1. System displays the username, email address and the phone number for the desired user                                                                                  |
| Exceptions           | 1. The user whose contact information was requested does not exist                                                                                                        |
|                      | 1.1 System displays an error message indicating the user does not exist                                                                                                   |
|                      | 1.2 System goes back go previous screen                                                                                                                                   |
| Qualities            | System is fast, responsive and reactive                                                                                                                                   |
| Constraints          |                                                                                                                                                                           |
| Includes             |                                                                                                                                                                           |
| Extends              |                                                                                                                                                                           |
| Related Artifacts    |                                                                                                                                                                           |
| Notes                | Since no user stories for the creation of accounts for care-providers have been given, the requirements for patient’s user profiles have been extended to care-providers. |
| Open Issues          |                                                                                                                                                                           |

### FetchProblemsAndRecordsWithAllKeywords <a name="FetchProblemsAndRecordsWithAllKeywords"></a>
| Use Case 11          | FetchProblemsAndRecordsWithAllKeywords                                                                                                                                                                                              |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 04.01.01                                                                                                                                                                                                                         |
| Participating Actors | User                                                                                                                                                                                                                                |
| Goal                 | Show all problems and records that contain all the keywords in a given list of problems                                                                                                                                             |
| Trigger              | User searches for keywords in problems and records                                                                                                                                                                                  |
| Precondition         | 1. User must be online                                                                                                                                                                                                              |
|                      | 2. User must know the key words the user wants to search                                                                                                                                                                            |
| Postcondition        | On success:                                                                                                                                                                                                                         |
|                      | 1. System displays the problems and records that contain all the keywords from a given list of problems                                                                                                                             |
| Basic Flow           | 1. System searches the problems and records that contain all the keywords from a given list of problems                                                                                                                             |
|                      | 2. System displays all the problem and records that contain all the keywords                                                                                                                                                        |
| Exceptions           | 1. No records exist that match all the keywords                                                                                                                                                                                     |
|                      | 1.1 System displays an error message indicating no records exist that match all the keywords                                                                                                                                        |
| Qualities            | System is fast, responsive and reactive.                                                                                                                                                                                            |
| Constraints          | User can only search problems and records from a given list. This list will be problems that a user has created(in case the user is a patient) or a list of problems for a particular patient(in case the user is a care-provider). |
| Includes             |                                                                                                                                                                                                                                     |
| Extends              |                                                                                                                                                                                                                                     |
| Related Artifacts    |                                                                                                                                                                                                                                     |
| Notes                |                                                                                                                                                                                                                                     |
| Open Issues          |                                                                                                                                                                                                                                     |

### FetchProblemsAndRecordsWithKeyWordsAndGeoLocatioin <a name="FetchProblemsAndRecordsWithKeyWordsAndGeoLocatioin"></a>
| Use Case 12          | FetchProblemsAndRecordsWithKeyWordsAndGeoLocatioin                                                                                                                                                                                  |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 04.02.01                                                                                                                                                                                                                         |
| Participating Actors | User                                                                                                                                                                                                                                |
| Goal                 | Show the user all problems and records that contain the keywords and are near a geo-location in a given list of problems.                                                                                                           |
| Trigger              | User searches for keywords in problems and records near a geo-location.                                                                                                                                                             |
| Precondition         | 1. User must be online                                                                                                                                                                                                              |
|                      | 2. User must know the key words the user wants to search                                                                                                                                                                            |
|                      | 3. User must know the geo-location near which they want to find records or user must know the body location near which the user wants to search.                                                                                    |
| Postcondition        | On success:                                                                                                                                                                                                                         |
|                      | 1. System displays the problems and records that contain the keywords and are near the specified geo-location or body-location from a given list of problems                                                                        |
| Basic Flow           | 1. System searches the problems and records that contain the keywords and are near the specified geo-location or body-location from a given list of problems                                                                        |
|                      | 2. System displays all the problem and records that contain the keywords and are near the specified geo-location or body-location                                                                                                   |
| Exceptions           | 1. No records exist that match the keywords and the body location or geo-location                                                                                                                                                   |
|                      | 1.1 System displays an error message indicating no records exist that match the keywords and the geo-location or body-location                                                                                                      |
| Qualities            | System is fast, responsive and reactive.                                                                                                                                                                                            |
| Constraints          | User can only search problems and records from a given list. This list will be problems that a user has created(in case the user is a patient) or a list of problems for a particular patient(in case the user is a care-provider). |
| Includes             |                                                                                                                                                                                                                                     |
| Extends              |                                                                                                                                                                                                                                     |
| Related Artifacts    |                                                                                                                                                                                                                                     |
| Notes                |                                                                                                                                                                                                                                     |
| Open Issues          |                                                                                                                                                                                                                                     |

### ViewAssignedPatients <a name="ViewAssignedPatients"></a>
| Use Case 13          | ViewAssignedPatients                                                                              |
|----------------------|---------------------------------------------------------------------------------------------------|
| Related User Story   | US 06.01.01                                                                                       |
| Participating Actors | User                                                                                              |
| Goal                 | View the list of patients a care-provider/user is assigned to                                     |
| Trigger              | User takes show all patients I am assigned to action                                              |
| Precondition         | 1. User must be online                                                                            |
|                      | 2. User must be care-provider                                                                     |
| Postcondition        | On success:                                                                                       |
|                      | 1. System displays the list of patients the care-provider is assigned to                          |
| Basic Flow           | 1. System displays the list of patients the care-provider is assigned to                          |
| Exceptions           | 1. Care-provider is not assigned to any patients                                                  |
|                      | 1.1 System displays an error message indicating the care-provider is not assigned to any patients |
| Qualities            | System is fast, responsive and reactive.                                                          |
| Constraints          |                                                                                                   |
| Includes             |                                                                                                   |
| Extends              |                                                                                                   |
| Related Artifacts    |                                                                                                   |
| Notes                |                                                                                                   |
| Open Issues          |                                                                                                   |

### AddNewPatient <a name="AddNewPatient"></a>
| Use Case 14          | AddNewPatient                                                                                                                 |
|----------------------|-------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 07.01.01                                                                                                                   |
| Participating Actors | User                                                                                                                          |
| Goal                 | Add a patient that the user(care-provider) has been assigned to.                                                              |
| Trigger              | User enters a patient's userid/username and takes the add patient action                                                      |
| Precondition         | 1. User must be online                                                                                                        |
|                      | 2. User must be care-provider                                                                                                 |
|                      | 3. User must know the userid/username for the patient to be added                                                             |
|                      | 4. User to be added userid/username must exist                                                                                |
| Postcondition        | On success:                                                                                                                   |
|                      | 1. System displays a message indicating that a new patient has been added to the assigned patients list for the care provider |
| Basic Flow           | 1. System searches for the patient with the given userid/username                                                             |
|                      | 2. System adds the patient to the list of user’s the care-provider is assigned to                                             |
| Exceptions           | 1. Patient/User with the given userid/username does not exist                                                                 |
|                      | 1.1 System displays a message indicating a patient/user with the given userid/username does not exist                         |
|                      | 1.2 System goes back to the previous screen.                                                                                  |
| Qualities            | System is fast, responsive and reactive.                                                                                      |
| Constraints          |                                                                                                                               |
| Includes             |                                                                                                                               |
| Extends              |                                                                                                                               |
| Related Artifacts    |                                                                                                                               |
| Notes                |                                                                                                                               |
| Open Issues          |                                                                                                                               |

### ViewProblemsForPatient <a name="ViewProblemsForPatient"></a>
| Use Case 15          | ViewProblemsForPatient                                                         |
|----------------------|--------------------------------------------------------------------------------|
| Related User Story   | US 07.01.01                                                                    |
| Participating Actors | User                                                                           |
| Goal                 | View all the problems for a given patient                                      |
| Trigger              | User takes the action to view all the problems for a given patient             |
| Precondition         | 1. User must be online                                                         |
|                      | 2. User must be care-provider                                                  |
|                      | 3. User must have the patient already added to their list                      |
| Postcondition        | On success:                                                                    |
|                      | 1. System displays all the problems for a given patient                        |
| Basic Flow           | 1. System searches for all the problems for a given patient                    |
|                      | 2. System displays all the problems for a given patient                        |
| Exceptions           | 1. No problems for a given patient exist                                       |
|                      | 1.1 System displays a message indicating no problems for a given patient exist |
| Qualities            | System is fast, responsive and reactive.                                       |
| Constraints          |                                                                                |
| Includes             |                                                                                |
| Extends              |                                                                                |
| Related Artifacts    |                                                                                |
| Notes                |                                                                                |
| Open Issues          |                                                                                |

### AddCommentToRecord <a name="AddCommentToRecord"></a>
| Use Case 16          | AddCommentToRecord                                                                  |
|----------------------|-------------------------------------------------------------------------------------|
| Related User Story   | US 02.07.01, US 07.01.01                                                            |
| Participating Actors | User                                                                                |
| Goal                 | Add comment to a given record                                                       |
| Trigger              | User takes action that adds a comment to a given record after entering a comment    |
| Precondition         | 1. User must be online                                                              |
|                      | 2. User must be care-provider                                                       |
|                      | 3. User must have the patient already added to their list                           |
|                      | 4. User must know the record and problem for which they want to add a comment       |
|                      | 5. The record for which the user wants to add the comment already exists            |
| Postcondition        | On success:                                                                         |
|                      | 1. System displays a message indicating that a comment has been added to the record |
| Basic Flow           | 1. System adds the given comment to the desired record                              |
|                      | 2. System displays a success message                                                |
| Exceptions           |                                                                                     |
| Qualities            | System is fast, responsive and reactive.                                            |
| Constraints          |                                                                                     |
| Includes             |                                                                                     |
| Extends              |                                                                                     |
| Related Artifacts    |                                                                                     |
| Notes                |                                                                                     |
| Open Issues          |                                                                                     |

### ViewGeoLocationForRecord <a name="ViewGeoLocationForRecord"></a>
| Use Case 17          | ViewGeoLocationForRecord                                                                                                                                                                         |
|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 10.02.01                                                                                                                                                                                      |
| Participating Actors | User                                                                                                                                                                                             |
| Goal                 | User views the geo-location of a record on a map                                                                                                                                                 |
| Trigger              | User chooses the view geo-location of a record on a map action                                                                                                                                   |
| Precondition         | 1. User knows the record for which they want to view the geo-location                                                                                                                            |
|                      | 2. If user is care-provider, ensure they are assigned the patient and user is online.                                                                                                            |
|                      | 3. If a user is patient, no other requirements need to be fulfilled                                                                                                                              |
| Postcondition        | On success:                                                                                                                                                                                      |
|                      | 1. The geo-location of a record is displayed on a map                                                                                                                                            |
| Basic Flow           | 1. System displays the geo-location of a record on a map                                                                                                                                         |
| Exceptions           | 1. No geo-location for the record exists                                                                                                                                                         |
|                      | 1.1 System displays a message indicating no geo-location for the record exists                                                                                                                   |
|                      | 1.2 System goes back to the previous screen                                                                                                                                                      |
| Qualities            | System is fast, responsive and reactive. If system is offline, the locally saved records are shown if the user is a patient. If the user is a care-provider and offline, error message is shown. |
| Constraints          |                                                                                                                                                                                                  |
| Includes             |                                                                                                                                                                                                  |
| Extends              |                                                                                                                                                                                                  |
| Related Artifacts    |                                                                                                                                                                                                  |
| Notes                |                                                                                                                                                                                                  |
| Open Issues          |                                                                                                                                                                                                  |

### ViewGeoLocationForAllRecords <a name="ViewGeoLocationForAllRecords"></a>
| Use Case 18          | ViewGeoLocationForAllRecords                                                                                                                                                                     |
|----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Related User Story   | US 10.03.01                                                                                                                                                                                      |
| Participating Actors | User                                                                                                                                                                                             |
| Goal                 | User views the geo-locations of all records on a map                                                                                                                                             |
| Trigger              | User chooses view geo-locations of all records on a map action                                                                                                                                   |
| Precondition         | 1. If a user is a patient, no other requirements need to be fulfilled                                                                                                                            |
|                      | 2. If user is a care-provider, ensure they are assigned the patient and the user is online. They must also choose the patient before triggering this action.                                     |
| Postcondition        | On success:                                                                                                                                                                                      |
|                      | 1. The geo-location of all records for a patient is displayed on a map                                                                                                                           |
| Basic Flow           | 1. System displays the geo-location of all records for a patient on a map                                                                                                                        |
| Exceptions           | 1. No records with geo-locations exist                                                                                                                                                           |
|                      | 1.1 System displays a message indicating no records with geo-locations exist                                                                                                                     |
| Qualities            | System is fast, responsive and reactive. If system is offline, the locally saved records are shown if the user is a patient. If the user is a care-provider and offline, error message is shown. |
| Constraints          |                                                                                                                                                                                                  |
| Includes             |                                                                                                                                                                                                  |
| Extends              |                                                                                                                                                                                                  |
| Related Artifacts    |                                                                                                                                                                                                  |
| Notes                |                                                                                                                                                                                                  |
| Open Issues          |                                                                                                                                                                                                  |

### UploadBodyLocationPhotos <a name="UploadBodyLocationPhotos"></a>
| Use Case 19           | UploadBodyLocationPhotos  |
|----------------------|---|
| Related User Story   |  US 09.03.01, US 11.01.01, US 11.03.01 |
| Participating Actors |  User, Sys Admin |
| Goal                 | User uploads front and back body location photos  |
| Trigger              | User chooses upload front and back body location photos action  |
| Precondition         | 1. User is online  |
|   |2. User is a patient  |
|   |3. User has already taken photos of front and back of their body  |
| Postcondition        | On success:  |
|   | 1. System displays a message that a user has uploaded photos of front and back of their body  |
| Basic Flow           | 1. System prompts the user to select front and back body location photos from the library  |
|   |2. User selects the images  |
|   |3. System prompts the user to specify which photo is of the front of the body, which image is of the back of the body.  |
|   |4. User specifies which photo is of the front of the body, which image is of the back of the body.  |
|   |5. User saves the photos  |
|   |6. System uploads the photos, if body location photos already exist, overwrite them.  |
| Exceptions           | 1. System does not have access to the photo library  |
|   |1.1 System displays a message indicating that the system does not have access to the photo library  |
|   |1.2 System prompts the user to fix the issue  |
|   |2a. The size of the selected images is larger than 65536 bytes  |
|   |2a.1 System displays a message indicating that the size of the selected images is larger than 65536 bytes  |
|   |2a.2 System prompts the user to fix the issue  |
|   |2b. User has tried to select more than 2 images  |
|   |2b.1 System displays a message indicating that only two images should be selected  |
|   |2b.2 System prompts the user to fix the issue  |
| Qualities            | System is fast, responsive and reactive.  |
| Constraints          |   |
| Includes             |   |
| Extends              |   |
| Related Artifacts    |   |
| Notes                |   |
| Open Issues          |   |

### ViewBodyLocationOfRecord <a name="ViewBodyLocationOfRecord"></a>
| Use Case 20          | ViewBodyLocationOfRecord                                                                            |
|----------------------|-----------------------------------------------------------------------------------------------------|
| Related User Story   | US 11.04.01                                                                                         |
| Participating Actors | User                                                                                                |
| Goal                 | View body location of record                                                                        |
| Trigger              | User chooses the view body location of record action                                                |
| Precondition         | 1. User is a patient                                                                                |
|                      | 2. User has already uploaded front and back body location photos                                    |
| Postcondition        | On success:                                                                                         |
|                      | 1. System displays body location picture with a clear marker for the record                         |
| Basic Flow           | 1. System displays body location photos with the body location of the record as a clear marker.     |
| Exceptions           | 1. No body location for the record exists                                                           |
|                      | 1.1 System displays message indicating no body location for the record exists                       |
| Qualities            | System is fast, responsive and reactive. If system is offline, the locally saved records are shown. |
| Constraints          |                                                                                                     |
| Includes             |                                                                                                     |
| Extends              |                                                                                                     |
| Related Artifacts    |                                                                                                     |
| Notes                |                                                                                                     |
| Open Issues          |                                                                                                     |
