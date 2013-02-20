Hakken REST API Specification
============================

Base
====

Method: **GET**

URI: ```/```

Returns:
```javascript
[
  "Jobs": "/jobs/for/{username}",
  "Tasks": "/tasks"
]
```

Authentication
==============

MUST be accessed via https.

The approach taken for authentication of calls is similar to http://www.thebuzzmedia.com/designing-a-secure-rest-api-without-oauth-authentication/
Virtually all requests require a valid hmacSHA1 hash to be passed in order for the request to be successful.

Method: **POST**

URI: ```/auth/login```

Params:

1. username
2. password

Returns:

(Successful login response)

```javascript
{
    "success": true,
    "token": "123456abcdef"
}
```
(Unsuccessful login response)
```javascript
{
    "success": false,
    "reason": "The reason login failed."
}
```
Jobs
====

```/jobs```

List for user
-------------

Method: **GET**

URI: ```/jobs/for/{username}```

URI: ```/jobs/for/{username}/since/{timestamp}```

Authenticated Form:

URI: ```/jobs/for/{username}/since/{timestamp}?hmac={hmac}```

{hmac} should be the hmacSHA1 hashed form of the string:

```timestamp={timestamp}&username={username}```

Returns:
```javascript
{
  "id": 1,
  "name": "job_1",
  "person": {...},
  "created": "2012-01-01 09:00",
  "due": "2012-01-30 17:00",
  "status": "",
  "groupname": "",
  "notes": "",
  "task": []
}
```
task can either be a full task definition or the url for a task

Specific job
------------

Method: **GET**

URI: ```/jobs/{id}```


Tasks
=====

URI: ```/tasks```

Specific task
-------------

Method: **GET**

URI: ```/tasks/{id}```
or
URI: ```/tasks/{name}```

Sample:
```javascript
{
  "id": 1,
  "name": "test_task"
  "description": "Test Task",
  "pages": [
    {
      "name": "page_1",
      "title": "Page 1",
      "items": [
        {
          "name": "item1",
          "label": "An Item",
          "type": "TEXT"
        }
      ]
    }
  ]
}
```
Alternative:
(but this wouldn't be good for mobile use due to reliance on having a
network connection)
```javascript
{
  "id": 1,
  "name": "test_task"
  "description": "Test Task",
  "pages": [
    "/tasks/test_task/pages/page_1",
    "/tasks/test_task/pages/page_2"
  ]
}
```
Could use HTTP "Accept:" headers to choose between the forms? 

Submissions
===========

URI: ```/submissions```

Submit job
----------

Method: **POST**

Authenticated Form:

URI: ```/submissions/from/{username}/?hmac={hmac}```

The trailing slash after {username} is required (due to idosyncrasies in the Spring MVC implementation of the server)

{hmac} should be the hmacSHA1 hashed form of the string:

```username={username}```

Submitted data:
```javascript
{
  "username": "some.user",
  "jobId": 1,
  "dataitems": [
      ...
  ]
}
```