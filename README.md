# spring-boot-note-api

This is a REST API for managing notes.

The project uses SpringBoot as the backend framework and MongoDB as the database.

## Run with docker
`docker run --name note-api -p 8080:8080 -e MONGODB_CONNSTRING=mongodb+srv://username:password@clusterURL lupusbytes/spring-boot-note-api`

## Features
- Create notes, optionally with tags.
- List notes with optional filtering of tags.
- Update notes
- Delete notes
- Calculate the number of unique words in a note.

# Endpoints

## GET ```/notes```

Optional query parameters include: ```pageSize```, ```pageNo``` and ```tags```

Returns a page of note ids, titles, createdDate and tags.

Response example:
```
{
    "currentPage": 0,
    "totalPages": 2,
    "totalRecords": 3,
    "records": [
        {
            "id": "962c8d5f-8494-4681-b2e9-2e128f35f2c9",
            "title": "Random thoughts",
            "createdDate": "2022-05-06T22:39:30.445+00:00",
            "tags": [
                "PERSONAL"
            ]
        },
        {
            "id": "d1c660f0-1141-4a33-bd7d-bad55f7a2011",
            "title": "Meeting with Mr. Bean",
            "createdDate": "2022-05-06T22:39:25.868+00:00",
            "tags": [
                "BUSINESS",
                "IMPORTANT"
            ]
        }
    ]
}
```

## POST ```/notes```

Creates a new note. 

`title` and `text` is required, but `tags` are optional.

Returns the persisted note.

Request body example:
```
{
    "title":"My note title",
    "text":"My note text",
    "tags": ["PERSONAL", "IMPORTANT"]
}
```


## PUT ```/notes/{id}```

Create or update a new note with the given `id`

`title` and `text` is required, but `tags` are optional.

Returns the persisted note.

Request body example:
```
{
    "title":"My note title",
    "text":"My note text",
    "tags": ["PERSONAL", "IMPORTANT"]
}
```

## DELETE ```/notes/{id}```
Deletes a note with the given `id`

Returns HTTP status `204` or `404` depending on if a note exists with this `id`.

## GET ```/notes/{id}/text```

Returns the text of the given note `id`.

Response body example:
```
{
    "text": "My note text"
}
```

## GET ```/notes/{id}/stats```

Calculates and returns the number of unique words in a note.

If the note text is `note is just a note`, the response body would be:
```
{
    "note": 2,
    "is": 1,
    "just": 1, 
    "a": 1
}
```
