# Facility Booking API

A REST API for managing facility bookings at an institution. Users can browse facilities, make bookings, check availability, and manage existing reservations.

**Live URL:** https://facilitybooking-lkpk.onrender.com

> **Note:** The server runs on Render's free tier and may take ~30 seconds to respond after inactivity (cold start).

---

## What it does

- Browse available facilities (rooms, labs, auditoriums)
- Book a facility for a specific date and time slot
- Check if a facility is already booked on a given date
- Update or cancel an existing booking

---

## Base URL

```
https://facilitybooking-lkpk.onrender.com
```

---

## Data Models

### Facility
| Field | Type | Description |
|---|---|---|
| `id` | number | Auto-generated ID |
| `name` | string | Facility name (e.g. "Computer Lab 1") |
| `location` | string | Building/block location |
| `capacity` | number | Max number of people |

### User
| Field | Type | Description |
|---|---|---|
| `id` | number | Auto-generated ID |
| `name` | string | Full name |
| `email` | string | Unique email address |
| `role` | string | `ADMIN` or `STUDENT` |

### Booking
| Field | Type | Description |
|---|---|---|
| `id` | number | Auto-generated ID |
| `facility` | object | Full facility object |
| `user` | object | Full user object |
| `date` | string | Date in `YYYY-MM-DD` format |
| `startTime` | string | Start time in `HH:MM` format |
| `endTime` | string | End time in `HH:MM` format |
| `status` | string | `CONFIRMED`, `PENDING`, or `CANCELLED` |

---

## Endpoints

### Facilities

#### Get all facilities
```
GET /facilities
```

**Response `200 OK`**
```json
[
  {
    "id": 1,
    "name": "Main Auditorium",
    "location": "Block A",
    "capacity": 500
  },
  {
    "id": 2,
    "name": "Computer Lab 1",
    "location": "Block B",
    "capacity": 40
  },
  {
    "id": 3,
    "name": "Meeting Room 3",
    "location": "Block C",
    "capacity": 15
  }
]
```

---

#### Get a facility by ID
```
GET /facilities/{id}
```

**Path parameter**
| Parameter | Type | Description |
|---|---|---|
| `id` | number | The facility ID |

**Response `200 OK`**
```json
{
  "id": 2,
  "name": "Computer Lab 1",
  "location": "Block B",
  "capacity": 40
}
```

**Response `404 Not Found`** — if the facility ID does not exist.

---

### Bookings

#### Get all bookings
```
GET /bookings
```

Returns all bookings with full facility and user details included.

**Response `200 OK`**
```json
[
  {
    "id": 1,
    "facility": {
      "id": 1,
      "name": "Main Auditorium",
      "location": "Block A",
      "capacity": 500
    },
    "user": {
      "id": 2,
      "name": "Bob Smith",
      "email": "bob@example.com",
      "role": "STUDENT"
    },
    "date": "2026-02-20",
    "startTime": "09:00:00",
    "endTime": "11:00:00",
    "status": "CONFIRMED"
  }
]
```

---

#### Create a booking
```
POST /bookings
Content-Type: application/json
```

**Request body**
| Field | Type | Required | Description |
|---|---|---|---|
| `user_id` | number | Yes | ID of the user making the booking |
| `facility_id` | number | Yes | ID of the facility to book |
| `date` | string | Yes | Date in `YYYY-MM-DD` format |
| `start_time` | string | Yes | Start time in `HH:MM` format |
| `end_time` | string | Yes | End time in `HH:MM` format |

**Example request**
```json
{
  "user_id": 2,
  "facility_id": 1,
  "date": "2026-03-15",
  "start_time": "10:00",
  "end_time": "12:00"
}
```

**Response `201 Created`**
```json
{
  "id": 4,
  "facility": { "id": 1, "name": "Main Auditorium", "location": "Block A", "capacity": 500 },
  "user": { "id": 2, "name": "Bob Smith", "email": "bob@example.com", "role": "STUDENT" },
  "date": "2026-03-15",
  "startTime": "10:00:00",
  "endTime": "12:00:00",
  "status": "CONFIRMED"
}
```

**Response `400 Bad Request`** — if user or facility ID does not exist.
```json
{ "error": "User not found" }
```
```json
{ "error": "Facility not found" }
```

---

#### Update a booking
```
PUT /bookings/{id}
Content-Type: application/json
```

**Path parameter**
| Parameter | Type | Description |
|---|---|---|
| `id` | number | The booking ID to update |

All fields are optional — only include the fields you want to change.

**Request body**
| Field | Type | Description |
|---|---|---|
| `date` | string | New date in `YYYY-MM-DD` format |
| `start_time` | string | New start time in `HH:MM` format |
| `end_time` | string | New end time in `HH:MM` format |
| `status` | string | New status: `CONFIRMED`, `PENDING`, or `CANCELLED` |

**Example — reschedule a booking**
```json
{
  "date": "2026-03-20",
  "start_time": "14:00",
  "end_time": "16:00"
}
```

**Example — cancel a booking**
```json
{
  "status": "CANCELLED"
}
```

**Response `200 OK`** — returns the updated booking object.

**Response `404 Not Found`** — if the booking ID does not exist.

---

#### Delete a booking
```
DELETE /bookings/{id}
```

**Path parameter**
| Parameter | Type | Description |
|---|---|---|
| `id` | number | The booking ID to delete |

**Response `204 No Content`** — booking deleted successfully, no body returned.

**Response `404 Not Found`** — if the booking ID does not exist.

---

### Availability

#### Check facility availability
```
GET /availability?facility_id={id}&date={YYYY-MM-DD}
```

**Query parameters**
| Parameter | Type | Required | Description |
|---|---|---|---|
| `facility_id` | number | Yes | The facility ID to check |
| `date` | string | Yes | Date in `YYYY-MM-DD` format |

**Example**
```
GET /availability?facility_id=1&date=2026-03-15
```

**Response `200 OK` — facility is free**
```json
{
  "facility_id": 1,
  "date": "2026-03-15",
  "available": true,
  "message": "No bookings found. Facility is fully available."
}
```

**Response `200 OK` — facility has existing bookings**
```json
{
  "facility_id": 1,
  "date": "2026-02-20",
  "available": false,
  "existing_bookings": [
    {
      "id": 1,
      "date": "2026-02-20",
      "startTime": "09:00:00",
      "endTime": "11:00:00",
      "status": "CONFIRMED"
    }
  ]
}
```

---

## Quick Reference

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/facilities` | List all facilities |
| `GET` | `/facilities/{id}` | Get one facility |
| `GET` | `/bookings` | List all bookings |
| `POST` | `/bookings` | Create a booking |
| `PUT` | `/bookings/{id}` | Update a booking |
| `DELETE` | `/bookings/{id}` | Delete a booking |
| `GET` | `/availability?facility_id=&date=` | Check availability |

---

## Sample Data

The database is pre-loaded with the following records for testing.

**Users**
| ID | Name | Email | Role |
|---|---|---|---|
| 1 | Alice Johnson | alice@example.com | ADMIN |
| 2 | Bob Smith | bob@example.com | STUDENT |
| 3 | Carol White | carol@example.com | STUDENT |

**Facilities**
| ID | Name | Location | Capacity |
|---|---|---|---|
| 1 | Main Auditorium | Block A | 500 |
| 2 | Computer Lab 1 | Block B | 40 |
| 3 | Meeting Room 3 | Block C | 15 |

---

## Tech Stack

- **Java 17** + **Spring Boot 4.0.2**
- **PostgreSQL** — hosted on Render
- **Hibernate** — auto-manages database schema
- **Maven** — build tool
- **Docker** — containerized deployment
