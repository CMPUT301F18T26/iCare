## Table of Content<sup>[*](#user_stories_footnote)</sup>

1. [Task Basics](#task_basics)
2. [Record Details](#record_details)
3. [User Profile](#user_profile)
4. [Searching](#searching)
5. [Patient Assigned](#patient_assigned)
6. [Care Provider](#care_provider)
7. [Offline Behaviour](#offline_behaviour)
8. [Photographs](#photographs)
9. [Geolocation and Maps](#geolocation_and_maps)
10. [Body-locations](#body_locations)

## Task Basics <a name="task_basics"></a>

- US 01.01.01 As a patient I want to record my problems, each denoted with a title, date started, and a brief description.

- US 01.01.02 (added 2018-02-14) As a patient, I want the maximum length of the task title to be at least 30 characters.

- US 01.01.03 (added 2018-02-14) As a patient, I want the maximum length of the task description to be at least 300 characters.

- US 01.02.01 As a patient, I want to view a list of my problems, with their titles, dates, and number of records.

- US 01.03.01 (revised 2018-02-16) As a patient, I want to edit the details for any one of my problems.

- US 01.04.01 As a patient, I want to delete a problem of mine.

- US 01.05.01 As a patient I want to add records (photos, locations, comments) to problems.

## Record Details <a name="record_details"></a>

- US 02.01.01 As a patient or care provider, I want to view all the records for a given problem, including its title, description, photo, etc.

- US 02.02.01 As a patient, I want to add records to problems.

- US 02.03.01 As a patient, records can have a geo-location.

- US 02.04.01 As a patient, records can have a body location.

- US 02.05.01 As a patient, records can have a title and comment.

- US 02.06.01 As a patient, records can have a photo.

- US 02.07.01 As care provider, I want to add new comment records to a problem for my patients.

- US 02.08.01 As a patient, records have a timestamp.

- US 02.09.01 As a patient, I want some method of helping me take consistent photos, so that when I show the doctor any progression such as the growth of a mole is evident.

- US 02.10.01 As a patient, I need to have a slideshow mode whereby photo records of a problem can be browsed by clicking. So I could "animate” changes to a doctor.


## User Profile <a name="user_profile"></a>

- US 03.01.01 As a patient, I want a profile with a unique userid and my contact information.

- US 03.01.02 As a patient, I want the minimum userid to be at least 8 characters.

- US 03.01.03 As a patient, I want the contact information to include an email address and a phone number.

- US 03.02.01 As a patient, I want to edit the contact information in my profile.

- US 03.03.01 As a patient or care provider, I want to, whenever a username is shown, be able to retrieve and see its corresponding contact information.

## Searching <a name="searching"></a>

- US 04.01.01 As a patient or care provider, I want to specify a set of keywords, and search for all problems or records that contains all the keywords.

- US 04.02.01 As a patient or care provider, I want to specify a set of keywords, and search for all problems or records that are near a particular Geo-location.

- US 04.02.01 As a patient or care provider, I want to specify a set of keywords, and search for all problems or records that are near a particular body-location.

## Patient Assigned <a name="patient_assigned"></a>

- US 06.01.01 As a care provider, I want to view a list of patients I am assigned to.

## Care Provider <a name="care_provider"></a>

- US 07.01.01 As a care provider, I want to add patients that I am assigned to.

- US 07.01.01 As a care provider, I want to want to browse my patients problems.

- US 07.01.01 As a care provider, I want to add comment records to my patients' problems

## Offline Behaviour <a name="offline_behaviour"></a>

- US 08.01.01 As a patient, I want to add or edit problems and records while off the network, and have these changes synchronized once I regain connectivity.

## Photographs <a name="photographs"></a>

- US 09.01.01 As a patient, I want to optionally attach one or more photographs as further viewable details to a record of mine.

- US 09.01.02 (added 2018-04-06) As a patient, I want the maximum number of photographs that can be attached to a record to be at least 10.

- US 09.02.01 As a patient or care provider, I want to view any attached photograph for a record.

- US 09.03.01 As a sys admin, I want each photograph to be under 65536 bytes in size.

## Geolocation and Maps <a name="geolocation_and_maps"></a>

- US 10.01.01 As a patient, I want to specify a geo location on a map of a record.

- US 10.02.01 As a patient or provider, I want to view any geo location for a record, on a map.

- US 10.03.01 As a patient or care provider, I want to see a map of all records for a patient (that have locations).

## Body-locations <a name="body_locations"></a>

- US 11.01.01 As a patient, I want to upload at least front and back body-location photos so I may indicate where photo records were taken on my body.

- US 11.02.01 As a patient, body locations are effectively a reference to a body-location photo and a point location on the photo.

- US 11.03.01 As a patient, I should be able to have at least 2 body location pictures.

- US 11.04.01 As a patient, when I view a body location it should be a clear indicator of a point on a body-location picture.

<a name="user_stories_footnote">*</a> User Stories were fetched from the project description provided by Abram Hindle