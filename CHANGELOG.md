# Learning Platform Changelog

## 0.9.1
_release date: 2025-01-13_
 * Set hard width on video-containe
 * Fixed broken styling
 * Fixed broken button text position on safari
 * Fixed broken checkbox on safari

## 0.9.0
_release date: 2025-01-13_
 * Fixed loading Google sign-in button
 * Set 'ng serve --live-reload=false' in package.json
 * Scaling screen view

## 0.8.9
_release date: 2025-01-13_
* Improved styling (courses, sign-in)

## 0.8.8
_release date: 2025-01-13_
 * Improved sign-in styling

## 0.8.7
_release date: 2025-01-13_
 * Sign In page design (in progress)
 * Local nginx listens on 80 and 443 ports

## 0.8.6
_release date: 2025-01-13_
 * Renamed sign-up to sign-in and moved continue with google button to it
 * Created a new google id for localhost
 * Reconfigured local nginx to listen on 443

## 0.8.5
_release date: 2025-01-13_
 * Eperimenting with Google SignIn

## 0.8.4
_release date: 2025-01-13_
 * Installed @abacritt/angularx-social-login
 * Imported SocialAuthServiceConfig and set Google Client ID
 * Created google-signin component

## 0.8.3
_release date: 2025-01-13_
 * Created empty sign-up files (html, css, ts)
 * Upgrade to Angular 18

## 0.8.2
_release date: 2025-01-13_
 * Opening course on Enroll button click
 * Set selected checkbox to light green
 * Decreased body top margin

## 0.8.1
_release date: 2025-01-13_
 * Reshaped enrolled badge
 * Improved/Fixed selecting/updating frontend items
 * Set processed to false for initial courses

## 0.8.0
_release date: 2025-01-12_
 * Small frontend refactoring
 * Created S3 buckets for each course

## 0.7.9
_release date: 2025-01-12_
 * Rearrangement and reformatting

## 0.7.8
_release date: 2025-01-12_
 * Set interface method names to 'execute'
 * Refreshing page state after enrollment update

## 0.7.7
_release date: 2025-01-12_
 * Fixed List Courses functionality (taking data from enrollment if needed)
 * Removed incorrect enrollment mapping in CourseEntity

## 0.7.6
_release date: 2025-01-12_
 * Renamed UpdateCourse* to UpdateEnrollment*
 * Implemented UpdateEnrollmentServiceImpl

## 0.7.5
_release date: 2025-01-12_
 * Added artificial v* tp each item. Removed most items (but not chapters) for java course

## 0.7.4
_release date: 2025-01-12_
 * Updating course content from frontend

## 0.7.3
_release date: 2025-01-12_
 * Integrated UpdateCourseController with service
 * Implemented LoadCourseRepositoryAdapter and UpdateCourseController

## 0.7.2
_release date: 2025-01-12_
 * Added --black color
 * Added UpdateCourseService and its empty implementation
 * Implemented UpdateCourseServiceImpl

## 0.7.1
_release date: 2025-01-12_
 * Minor styling improvements
 * Added LoadCourseRepository and its empty adapter
 * Updated color names

## 0.7.0
_release date: 2025-01-12_
 * Finished new server setup
 * Created S3 bucket in Hetzner
 * Switched to Hetzner S3 bucket
 * Improvements for positioning video, pdf, and quiz

## 0.6.9
_release date: 2025-01-12_
 * Added app scripts
 * Remove margin from title na front page
 * Updated Server Setup section
 * Setting up new server

## 0.6.8
_release date: 2025-01-11_
 * Added SSL cert and key
 * Added nginx.conf
 * Updated server setup section

## 0.6.7
_release date: 2025-01-11_
 * Added Server Setup section in README
 * Installed main services on new server

## 0.6.6
_release date: 2025-01-11_
 * Installed new Hetzner server

## 0.6.5
_release date: 2025-01-11_
 * Created Hetzner account and ordered new server

## 0.6.4
_release date: 2025-01-11_
 * Analyzed Hetzner servers and storage offer

## 0.6.3
_release date: 2025-01-11_
 * Update Introduction and Getting Started sections to all courses

## 0.6.2
_release date: 2025-01-11_
 * First, simple performance tests with Apache Benchmark

## 0.6.1
_release date: 2025-01-11_
 * Removed unenrolled_time from enrollments table
 * Enabled gzip content encoding (backend)

## 0.6.0
_release date: 2025-01-11_
 * Fixed 'Unenroll Course' persistence

## 0.5.9
_release date: 2025-01-11_
 * Fixed DeleteEnrollmentRepositoryAdapter

## 0.5.8
_release date: 2025-01-11_
 * Implemented UnenrollCourseServiceImpl
 * Implemented DeleteEnrollmentRepositoryAdapter

## 0.5.7
_release date: 2025-01-11_
 * Added and configured rate limiter to nginx

## 0.5.6
_release date: 2025-01-11_
 * Removed MessageListenerAdapter
 * Created DeleteEnrollmentRepository and its empty adapter

## 0.5.5
_release date: 2025-01-10_
 * Added CheckEnrollmentRepository
 * Implemented CheckEnrollmentRepositoryAdapter

## 0.5.4
_release date: 2025-01-10_
 * Renamed courses-details to courses-toc
 * Added Unenroll button with backend call

## 0.5.3
_release date: 2025-01-10_
 * Calling /api/v1/course/{courseId}/enrollment by frontend

## 0.5.2
_release date: 2025-01-10_
 * Added maintenance page to nginx

## 0.5.1
_release date: 2025-01-10_
 * Add UNIQUE constraint on account_id and course_id pair in enrollments table
 * Upgrade to Angular 17.3.0

## 0.5.0
_release date: 2025-01-10_
 * Integrated EnrollCourseController with service
 * Added NOT NULL constraints for foreign keys
 * Improvements for 'Enroll Course' backend functionality

## 0.4.9
_release date: 2025-01-10_
 * Added CheckCourseRepository and its adapter
 * Implemented EnrollCourseServiceImpl (first draft)

## 0.4.8
_release date: 2025-01-10_
 * Removed SSL cert and key
 * Removed .editorconfig
 * Reformatted frontend code with 4 space indentation

## 0.4.7
_release date: 2025-01-10_
 * Shortened to long names in Java course TOC
 * Enabled virtual threads
 * Removed unused List/Load enrollment(s) functionality
 * Reformatted frontend code

## 0.4.6
_release date: 2025-01-10_
 * Updated TOC for java course (External Libraries)
 * Updated TOC for java course (Monitoring and Troubleshooting)
 * Updated TOC for java course (Preview and Incubator Features)

## 0.4.5
_release date: 2025-01-10_
 * Disabled logbook logging
 * Reading enrolled flag from database

## 0.4.4
_release date: 2025-01-10_
 * Added initial chapter to all courses
 * Updated TOC for java course (Foreign Memory API)
 * Updated TOC for java course (Performance Tuning)

## 0.4.3
_release date: 2025-01-10_
 * Updated TOC for java course (Math Operations)
 * Updated TOC for java course (Regular Expressions)
 * Updated TOC for java course (Networking)
 * Updated TOC for java course (Internationalization)
 * Updated TOC for java course (JDBC)

## 0.4.2
_release date: 2025-01-10_
 * Updated TOC for java course (Reflection)
 * Updated TOC for java course (Data-Oriented Programming)
 * Updated TOC for java course (Concurrency)
 * Replaced dashboard by courses

## 0.4.1
_release date: 2025-01-10_
 * Removed duplicate call to /api/v1/account endpoint
 * Improved styling and positioning

## 0.4.0
_release date: 2025-01-09_
 * Updated TOC for java course (Generics)
 * Updated TOC for java course (Records and Pattern Matching)
 * Updated TOC for java course (Modularization)
 * Updated TOC for java course (Date and Time)
 * Updated TOC for java course (Java I/O)

## 0.3.9
_release date: 2025-01-09_
 * Updated TOC for java course (Streams)
 * Added code to course model
 * Added code to controller
 * Replace course id by code in router

## 0.3.8
_release date: 2025-01-09_
 * Changed hover color to black
 * Fixed with of video and pdf components
 * Readjusted components properties (to improve visual effects)
 * Introduced black color schema on front pag

## 0.3.7
_release date: 2025-01-09_
 * Refreshed styling

## 0.3.6
_release date: 2025-01-09_
 * Prepared backend service on softhlon-learning.tech
 * Prepared frontend service on softhlon-learning.tech

## 0.3.5
_release date: 2025-01-09_
 * Updated TOC for java course (Object-Oriented Programming)
 * Updated TOC for java course (Project)
 * Updated TOC for java course (Data Structures)
 * Updated TOC for java course (Error Handling)
 * Updated TOC for java course (Functional Programming)

## 0.3.4
_release date: 2025-01-09_
 * Updated TOC for java course (Introduction, Getting Started, and Language Fundamentals)
 * Updated checked color (white)

## 0.3.3
_release date: 2025-01-09_
 * Configured nginx on localhost
 * Configured nginx on softhlon-learning.tech

## 0.3.2
_release date: 2025-01-09_
 * Updated color set
 * Installed nginx

## 0.3.1
_release date: 2025-01-08_
 * Loading content with course list
 * Updated app title
 * Unlocked course in progress screen

## 0.3.0
_release date: 2025-01-08_
 * Removed unnecessary course definitions
 * Added orderNo to the course model

## 0.2.9
_release date: 2025-01-08_
 * Implemented ListCoursesServiceImpl
 * Integrated ListCoursesService with controller and frontend

## 0.2.8
_release date: 2025-01-08_
 * Added LoadCoursesRepository and implemented its adapter
 * Replaced IO.SOFTHLON by SOFTHLON-LEARNING.TECH

## 0.2.7
_release date: 2025-01-08_
 * Minor error logging improvements
 * CreateCoursesOperator improvements
 * Added @RestApiAdapter to all controllers

## 0.2.6
_release date: 2025-01-08_
 * Refactored CreateCoursesOperator
 * Error logging in persistence adapters

## 0.2.5
_release date: 2025-01-08_
 * Added id, description and version to course definitions
 * Removed default id generation in courses table
 * Implemented UploadCourseServiceImpl

## 0.2.4
_release date: 2025-01-08_
 * Added UploadCourseService and related repository interfaces
 * Implemented UploadCourseService

## 0.2.3
_release date: 2025-01-08_
 * Added ModuleInitializer
 * Added empty CreateCoursesOperator
 * Read all course definitions in CreateCoursesOperator

## 0.2.2
_release date: 2025-01-08_
 * Added SSL certificate
 * Added SSL key
 * Run frontend with SSL on softhlon-learning.tech

## 0.2.1
_release date: 2025-01-08_
 * Created templates for course contents
 * Installed Postgres server on softhlon-learning.tech

## 0.2.0
_release date: 2025-01-07_
 * Installed production server
 * Configured domain softhlon-learning.tech

## 0.1.9
_release date: 2025-01-07_
 * Updated postgres password and exposed instance to localhost only

## 0.1.8
_release date: 2025-01-07_
 * Improved record naming in accounts module
 * Improved record naming in courses module
 * Improved record naming in subscription module

## 0.1.7
_release date: 2025-01-07_
 * Implemented controller for Subscribe
 * Created AuthenticationToken
 * Created mocked AuthenticationContext
 * Integrated AuthenticationContext with SubscribeController

## 0.1.6
_release date: 2025-01-07_
 * Implemented service for Subscribe functionality

## 0.1.5
_release date: 2025-01-07_
 * Added basic security and password encryption
 * Improved type naming for repository requests and results

## 0.1.4
_release date: 2025-01-07_
 * Improved type naming for SignUp functionality
 * Improved error handling for run.sh
 * Implemented validation by email for SignUp functionality

## 0.1.3
_release date: 2025-01-07_
 * Added request/response logging
 * Very first/draft implementation of SignUp functionality

## 0.1.2
_release date: 2025-01-07_
 * Created success helper method for controllers
 * Shortened field names in controllers

## 0.1.1
_release date: 2025-01-07_
 * Added update_modified_column function and defaults for created and updated times
 * Removed createdTime and updatedTime from code
 * Fixed controller method names

## 0.1.0
_release date: 2025-01-06_
 * Persistence adapters for accounts
 * Persistence adapters for courses
 * Persistence adapters for subscriptions
 * Release dates in changelog
 * Changelog title update

## 0.0.9
_release date: 2025-01-06_
 * Jpa repositories and entities for all modules
 * New created_time and updated_time columns for all tables

## 0.0.8
_release date: 2025-01-06_
 * Repository interfaces for accounts
 * Repository interfaces for courses
 * Repository interfaces for subscriptions

## 0.0.7
_release date: 2025-01-06_
 * Created basics database tables
 * Added foreign and unique keys

## 0.0.6
_release date: 2025-01-06_
 * Initial database and liquibase setup
 * Created docker-compose for postgres
 * Created module-info files for all modules

## 0.0.5
_release date: 2025-01-05_
 * Simplified and set modules configuration

## 0.0.4
_release date: 2025-01-05_
 * Added ResponseBodyHelper
 * Added controllers for all modules  

## 0.0.3
_release date: 2025-01-04_
 * Introduced sealed types in services 

## 0.0.2
_release date: 2025-01-04_
 * Added draft of inbound ports and their implementations

## 0.0.1
_release date: 2025-01-04_
 * Initial project structure
 * Copied frontend code from previous project
