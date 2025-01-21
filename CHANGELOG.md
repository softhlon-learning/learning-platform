# Learning Platform Changelog

## 0.21.5
_release date: 2025-01-21_
 * Optimized upgrade script a bit
 * Moved config folder one level up
 * Documented backend init script
 * Documented frontend init script
 * Documented upgrade script

## 0.21.4
_release date: 2025-01-21_
 * Created maintenance page
 * Added maintenance page to nginx config

## 0.21.3
_release date: 2025-01-21_
 * Secured server to run services on unprivileged user

## 0.21.2
_release date: 2025-01-21_
 * Finished /usr/local/etc/rc.d/frontend init script

## 0.21.1
_release date: 2025-01-21_
 * Tuned pool sizes
 * Building fat jar
 * Finished /usr/local/etc/rc.d/backend init script

## 0.21.0
_release date: 2025-01-21_
 * Working on init scripts

## 0.20.9
_release date: 2025-01-21_
 * Fixed cert key file name
 * Updated README for FreeBSD
 * Tuned performance parameters in application.yml
 * Updated run-prod.sh for right zsh path on FreeBSD

## 0.20.8
_release date: 2025-01-21_
 * Finished FreeBSD setup
 * First app run on new OS

## 0.20.7
_release date: 2025-01-21_
 * Installed maven and java ports
 * Installed postgresql port

## 0.20.6
_release date: 2025-01-21_
 * Fresh FreeBSD 14.2 installation (Hetzner)

## 0.20.5
_release date: 2025-01-20_
 * FreeBSD installation in progress

## 0.20.4
_release date: 2025-01-20_
 * Started migration to FreeBSD 14.2

## 0.20.3
_release date: 2025-01-20_
 * Very promising performance benchmarks on FreeBSD

## 0.20.2
_release date: 2025-01-20_
 * Installed FreeBSD 14.2 successfully (on OVH)

## 0.20.1
_release date: 2025-01-20_
* Re-evaluated FreeBSD as main server (positively)

## 0.20.0
_release date: 2025-01-20_
 * Moved DNS Zone to Hetzner

## 0.19.9
_release date: 2025-01-20_
 * Evaluated FreeBSD as main server (negatively)

## 0.19.8
_release date: 2025-01-20_
 * Refactoring: PDF -> Document
 * Replaced PDF with Document type in all courses
 * Added missing videos to Java course definition

## 0.19.7
_release date: 2025-01-20_
 * Created no-document-yet.html and uploaded it to each S3 bucket
 * Updated all document links to no-document-yet.html

## 0.19.6
_release date: 2025-01-20_
 * Updated all video links to no-video-yet.mov (leaving only welcome one untouched)

## 0.19.5
_release date: 2025-01-20_
 * Created no-video-yet.mov and uploaded it to each S3 bucket
 * Updated/fixed document-lecture component

## 0.19.4
_release date: 2025-01-20_
 * Minor tune of document display
 * Found a better way to generate html (via Grok)

## 0.19.3
_release date: 2025-01-20_
 * Refactored pdf-lecture to document-lecture

## 0.19.2
_release date: 2025-01-20_
 * Updated pdf-lecture to display html files
 * Support for ArrowLeft for Home button, on toc page

## 0.19.1
_release date: 2025-01-20_
 * Updated video link in all courses
 * Uploaded to S3 and tested HTML files instead of PDF

## 0.19.0
_release date: 2025-01-20_
 * Check if course is enrolled/unenrolled and authentication status on key [e,u] press
 * Extract keyboard-input.ts for toc
 * Extract keyboard-input.ts for details

## 0.18.9
_release date: 2025-01-20_
 * Display shortcut keys on toc page
 * Support for key press on toc page

## 0.18.8
_release date: 2025-01-19_
 * Improved styling for lecture list on course details
 * Added key shortcuts to Back and Home on course detail

## 0.18.7
_release date: 2025-01-19_
 * Added broder on course details main container
 * Hiding video controls when mouse is not over

## 0.18.6
_release date: 2025-01-19_
 * Automatic 'Mark as Viewed' when video is finished
 * Display navigation keys on buttons. Changed buttons width

## 0.18.5
_release date: 2025-01-19_
 * Title and info improvements on all side forms

## 0.18.4
_release date: 2025-01-19_
 * Added updateProfile service and fixed profile form logic
 * Improved error/success message handling for profile update

## 0.18.3
_release date: 2025-01-19_
 * Loading profile data from backend
 * Added support for saving profile on frontend

## 0.18.2
_release date: 2025-01-19_
 * Implemented GetProfileController
 * Added getProfile service

## 0.18.1
_release date: 2025-01-19_
 * Implemented GetProfileServiceImpl
 * Updated favicon.ico

## 0.18.0
_release date: 2025-01-19_
 * Created UpdateProfileController
 * Added GetProfileService and empty GetProfileServiceImpl

## 0.17.9
_release date: 2025-01-19_
 * Styling improvements for password recovery and profile forms
 * Added UpdateProfileService and empty UpdateProfileServiceImpl
 * Implemented UpdateProfileServiceImpl

## 0.17.8
_release date: 2025-01-19_
 * Minor redesign of profile page
 * Minor redesign of password recovery page
 * Renamed --sign-in to --side-form
 * Renamed *-item to *-lecture

## 0.17.7
_release date: 2025-01-19_
 * Fixed authentication error handling
 * Added deleteAccount() platform service
 * Integrated delete button with deleteAccount service

## 0.17.6
_release date: 2025-01-18_
 * Refactored unAuthorizedBody
 * Fixed SignInServiceImpl to check isDeleted flag
 * Fixed LoadAccountByEmailRepositoryAdapter to check isDeleted flag

## 0.17.5
_release date: 2025-01-18_
 * Implemented DeleteAccountController
 * Fixing account deletion

## 0.17.4
_release date: 2025-01-18_
 * Added DeleteAccountService
 * Implemented DeleteAccountServiceImpl

## 0.17.3
_release date: 2025-01-18_
 * Created PersistAccountRepository
 * Implemented PersistAccountRepositoryAdapter

## 0.17.2
_release date: 2025-01-18_
 * Removed enrollment status from model and database

## 0.17.1
_release date: 2025-01-18_
 * Added settings link in header
 * Renamed Settings with Profile and created a dedicated page

## 0.17.0
_release date: 2025-01-18_
 * Improved error messages
 * Deleted unused GetCourseDetails*

## 0.16.9
_release date: 2025-01-18_
 * Passing error message to /sign-in when account has been deleted before
 * Displaying error from query parameter

## 0.16.8
_release date: 2025-01-18_
 * Added loginRedirectFailUri to handle Google Sign In error
 * Propagating information that account has been deleted, when creating a new one with the same email

## 0.16.7
_release date: 2025-01-18_
 * Replaced account status by isDeleted
 * Checking isDeleted flag when querying for accounts

## 0.16.6
_release date: 2025-01-18_
 * Added Spring Boot banner
 * Small improvements in GoogleSignInServiceImpl
 * Added invalidated_tokens__token_hash_index
 * JwtService improvements/cleanup
 * SignUpServiceImpl improvements/cleanup

## 0.16.5
_release date: 2025-01-18_
 * Improved logging in UpdateEnrollmentController
 * Renamed /course/progress to /course/details
 * GoogleSignInController cleanup/refactoring
 * SignOutController cleanup
 * SignUpController cleanup

## 0.16.4
_release date: 2025-01-18_
 * Reordered top navigation buttons
 * Renamed NavigationItems to NavigationLectures
 * Renamed CourseProgress* to CourseDetails*

## 0.16.3
_release date: 2025-01-18_
 * Moved tokenHash and extractToken to JwtService

## 0.16.2
_release date: 2025-01-18_
 * Added NotAuthorized response to /auth/sign-out, when auth token is tno present
 * Checking if token has been invalidated

## 0.16.1
_release date: 2025-01-18_
 * Fixed persisting course code
 * Improved styling of chapters/lectures list
 * Not moving to the next when marked as not viewed

## 0.16.0
_release date: 2025-01-18_
 * Updated Microservices welcome video
 * Updated Spring Boot welcome video
 * Updated Fullstack welcome video* 
 * Created Microservices Design welcome video
 * Created Spring Boot welcome video
 * Created Microservices welcome video

## 0.15.9
_release date: 2025-01-18_
 * Updated Domain Driven Design welcome video
 * Updated Async Messaging welcome video
 * Created Domain Driven Design welcome video
 * Created Async Messaging welcome video

## 0.15.8
_release date: 2025-01-18_
 * Updated main app title
 * Updated Architecture welcome video 
 * Created Architecture welcome video

## 0.15.7
_release date: 2025-01-18_
 * Parametrized video-item component for passing course paths
 * Updated welcome video paths

## 0.15.6
_release date: 2025-01-18_
* Created Java welcome video
* Created Api Design welcome video

## 0.15.5
_release date: 2025-01-17_
 * Reseaching/Innovation session (new html viewer component)

## 0.15.4
_release date: 2025-01-17_
 * Tuning PDF viewer

## 0.15.3
_release date: 2025-01-17_
 * Optimized marking as viewed/not viewed flag
 * Few more display/refresh optimizations

## 0.15.2
_release date: 2025-01-17_
 * Replaced Reset by Home button
 * Moving to the next lecture after viewed flag change
 * Added switchLectureViewedFlag on press M key
 * Removed header text

## 0.15.1
_release date: 2025-01-17_
 * Updated Java course
 * Minor style tuning on course progress
 * Added Watch/Read/Interact labels

## 0.15.0
_release date: 2025-01-17_
 * Adjusted Java course to new model

## 0.14.9
_release date: 2025-01-17_
 * Updated course contents (without Java)
 * Displaying lecture typ

## 0.14.8
_release date: 2025-01-17_
 * Refactored frontend to the new course model

## 0.14.7
_release date: 2025-01-17_
 * Redesigned course content structure
 * Updated course model

## 0.14.6
_release date: 2025-01-17_
 * Synced JWT and auth cookies expiration time.

## 0.14.5
_release date: 2025-01-17_
 * Restructured sections/chapters/items list in course details

## 0.14.4
_release date: 2025-01-17_
 * Installed and configured Postfix

## 0.14.3
_release date: 2025-01-16_
 * Enabled autoplay for video
 * Added link to Password Recovery page
 * Added Password Recovery page

## 0.14.2
_release date: 2025-01-16_
 * Added simple Sign Up validation
 * Improved Sign Up error handling on frontend

## 0.14.1
_release date: 2025-01-16_
 * Hiding 'Sign In' link on 'Sign Up' page
 * Added info text on Sign Up page
 *  Creating Authorization cookies on Sign Up

## 0.14.0
_release date: 2025-01-16_
 * Sending 'Sign Up' request to backend

## 0.13.9
_release date: 2025-01-16_
 * Added 'Sign Up' link on 'Sing In' page
 * Added 'sign-up' page

## 0.13.8
_release date: 2025-01-16_
 * Improved request logging in GoogleSignInController
 * Added simple comments to accounts controllers
 * Added simple comments to courses controllers
 * Added simple comments to subscriptions controllers

## 0.13.7
_release date: 2025-01-16_
 * Added error message to 401 response
 * Displaying sign-in error message from backend

## 0.13.6
_release date: 2025-01-16_
 * Updated sigh-in form color and error message styling
 * Rendering home only when data is loaded

## 0.13.5
_release date: 2025-01-16_
 * Redirecting to /home after success
 * Displaying error message when 401

## 0.13.4
_release date: 2025-01-16_
 * Submitting sign-in form
 * Sending sign-in request to backend

## 0.13.3
_release date: 2025-01-16_
 * Added redirection to the right page after sign-in

## 0.13.2
_release date: 2025-01-16_
 * Renamed course.service.ts to platform.service.ts nad moved to dedicated folder
 * Created signIn() method

## 0.13.1
_release date: 2025-01-16_
 * Repaired broken routerLink in course-tile
 * Integrated SignOutController with AuthCookiesService

## 0.13.0
_release date: 2025-01-16_
 * Setting proper cookies on Sign In
 * Added AuthCookiesService
 * Adjusted controllers to new AuthCookiesService

## 0.12.9
_release date: 2025-01-16_
 * Implemented SignInServiceImpl
 * Returning 401 when authentication failed

## 0.12.8
_release date: 2025-01-16_
 * Moved security related classes to infrastructure
 * Created LoadAccountByEmailRepository and its empty adapter
 * Implemented LoadAccountByEmailRepositoryAdapter

## 0.12.7
_release date: 2025-01-16_
 * Displaying app version

## 0.12.6
_release date: 2025-01-16_
 * Added course-tile and used in home page

## 0.12.5
_release date: 2025-01-16_
 * Fix NPE in extractToken
 * Reformatting and optimized imports
 * Refactored courses to home

## 0.12.4
_release date: 2025-01-16_
 * Using real auth context instead of mocked one

## 0.12.3
_release date: 2025-01-16_
 * Redirecting to sign-in from Enroll/UnEnroll button, when needed
 * Added sign-in redirection also to Open button

## 0.12.2
_release date: 2025-01-15_
 * Created SecurityConfig and updated security config
 * Secured backend with JWT
 * Added add run-debug.sh script

## 0.12.1
_release date: 2025-01-15_ 
 * Improved sign-in styling
 * Added Subscribe link and bold on links hover

## 0.12.0
_release date: 2025-01-15_
 * Fixed overflow styling
 * Added missing html body
 * Increased size of course tiles
 * Fixed overlay on TOC page

## 0.11.9
_release date: 2025-01-15_
 * Changed 'Back' to 'Home' button
 * Added 'Enrolled' label
 * App-header styling improvement
 * Fixed ddd code

## 0.11.8
_release date: 2025-01-15_
 * Unsecured front page
 * Changed main page path (/home)
 * Hiding 'Sign In' link on sign-in page
 * Changed login-redirect-url to */home

## 0.11.7
_release date: 2025-01-15_
 * Added Fullstack Development course

## 0.11.6
_release date: 2025-01-15_
 * Updated/fix hover colors over course tiles

## 0.11.5
_release date: 2025-01-15_
 * Added description to each course tile
 * Updated course definitions with cool descriptions
 * Highlighted enrolled courses with greenish background

## 0.11.4
_release date: 2025-01-15_
 * Redesigned front page

## 0.11.3
_release date: 2025-01-15_
 * Proper auth token invalidation on backend

## 0.11.2
_release date: 2025-01-15_
 * Added app-header component
 * Refactored/improved GoogleSignInController
 * Sign out on frontend side

## 0.11.1
_release date: 2025-01-15_
 * Added 'Authenticated' cookie
 * Checking Authenticated cookie instead of Authorization
 * Added addAuthFailedCookies method
 * Added CookieService to all pages

## 0.11.0
_release date: 2025-01-14_
 * Improved sign-in styling and added Cancel button
 * Replaced --black by --main and slightly updated the color
 * Primitive redirection from /courses to /sign-in when Authorization cookie is not present

## 0.10.9
_release date: 2025-01-14_
 * Setting Authorization cookie in Google Sign In response
 * Added CookieService to courses.service.ts

## 0.10.8
_release date: 2025-01-14_
 * Implemented CustomAuthenticationManager
 * Generating JWT on Google Sign in
 * Added default constructor to AccountEntity

## 0.10.7
_release date: 2025-01-14_
 * Added JWTProcessor
 * Added User (for JWT)
 * Rearranged and optimized imports
 * Added JWT secret configuration params

## 0.10.6
_release date: 2025-01-14_
 * Refactored interface method names to execute
 * Logging every controller request

## 0.10.5
_release date: 2025-01-14_
 * Implemented CheckTokenRepositoryAdapter
 * AuthToken* to Token small refactoring

## 0.10.4
_release date: 2025-01-14_
 * Implemented CreateInvalidatedTokenRepositoryAdapter
 * Implemented SignOutServiceImpl

## 0.10.3
_release date: 2025-01-14_
 * Added CreateInvalidatedTokenRepository and its empty adapter
 * Fixed package folder names (to tech.softlon)

## 0.10.2
_release date: 2025-01-14_
 * Renamed io.softhlon to tech.softhlon

## 0.10.1
_release date: 2025-01-14_
 * Created table invalidated_tokens
 * Added CheckAuthTokenRepository and its empty adapter

## 0.10.0
_release date: 2025-01-14_
 * Creating new account on Google authentication, if needed

## 0.9.9
_release date: 2025-01-14_
 * Added type to account model
 * Removed console outputs from GoogleSignInServiceImpl
 * Partial implementation of GoogleSignInServiceImpl

## 0.9.8
_release date: 2025-01-14_
 * Added google-client-id to application.yml
 * First Google token verification on backend

## 0.9.7
_release date: 2025-01-14_
 * Analyzed Google Token verification on server side

## 0.9.6
_release date: 2025-01-14_
* Firewall setup

## 0.9.5
_release date: 2025-01-14_
 * Parametrized login-redirect-uri
 * Prepared run-local.sh and run-prod.sh scripts

## 0.9.4
_release date: 2025-01-14_
 * Increased size of sign-in form 
 * Implemented simple/POC GoogleSignInController and service, with redirection

## 0.9.3
_release date: 2025-01-14_
 * Parametrized loginUri
 * Reformatting frontend

## 0.9.2
_release date: 2025-01-13_
 * Parametrized googleClientId for local and production

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
