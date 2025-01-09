# Learning Platform Changelog

## 0.4.1
_release date: 2025-01-09_
 * Removed duplicate call to /api/v1/account endpoint
 * Improved styling and positioning

## 0.4.0
_release date: 2025-01-09_
 * Updated TOC for java course (Generics)
 * Updated TOC for java course (Records and Pattern Matching)
 * Updated TOC for java course (Modularization)
 * Updated TOC for java course (Date and Time)
 * Updated TOC for java course (Java I/O

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
