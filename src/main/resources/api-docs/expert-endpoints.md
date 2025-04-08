# Expert API Endpoints

## Update Expert Categories and Consulting Price

Updates an expert's categories and consulting price.

**URL:** `/api/{expertId}/categories`

**Method:** `PUT`

**Auth required:** Yes (Bearer Token)

**Permissions required:** Admin or the expert user themselves

### Path Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| expertId  | Long | ID of the expert to update |

### Request Body

```json
{
  "categoryIds": [1, 2, 3],
  "consultingPrice": 850000.0
}
```

| Field | Type | Description | Constraints |
|-------|------|-------------|------------|
| categoryIds | Array of Long | IDs of categories to assign to the expert | Cannot be empty |
| consultingPrice | Double | New consulting price for the expert | Must be positive |

### Success Response

**Code:** `200 OK`

**Content example:**

```json
{
  "id": 1,
  "language": "English",
  "ggMeetUrl": "https://meet.google.com/abc-defg-hij",
  "consultingPrice": 850000.0,
  "commission": 0.0,
  "averageRating": 4.8,
  "totalRatings": 25,
  "specializationLevel": 2,
  "categories": [
    {
      "id": 1,
      "name": "Communication",
      "description": "Effective communication strategies"
    },
    {
      "id": 2,
      "name": "Conflict Resolution",
      "description": "Techniques for resolving conflicts"
    },
    {
      "id": 3,
      "name": "Financial Planning",
      "description": "Planning finances as a couple"
    }
  ],
  "user": {
    "id": 1,
    "username": "expert1",
    "email": "expert1@example.com",
    "fullName": "John Expert",
    "gender": "MALE",
    "country": "USA",
    "address": "123 Main St",
    "phone": "1234567890",
    "birthday": "1985-05-15",
    "imgurl": "https://example.com/photos/expert1.jpg",
    "role": "ROLE_EXPERT",
    "eStatus": "APPROVED",
    "bio": "Expert in relationship counseling with 10 years of experience"
  }
}
```

### Error Response

**Condition:** If the expert is not found or categories are invalid

**Code:** `400 BAD REQUEST`

**Content example:**

```
Expert not found
```

or

```
One or more categories not found
```

### Sample Call (using RestTemplate)

```java
RestTemplate restTemplate = new RestTemplate();
HttpHeaders headers = new HttpHeaders();
headers.setContentType(MediaType.APPLICATION_JSON);
headers.set("Authorization", "Bearer " + jwtToken);

Map<String, Object> requestBody = new HashMap<>();
requestBody.put("categoryIds", Arrays.asList(1L, 2L, 3L));
requestBody.put("consultingPrice", 850000.0);

HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

ResponseEntity<ExpertResponse> response = restTemplate.exchange(
    "http://localhost:8080/api/1/categories", 
    HttpMethod.PUT, 
    entity, 
    ExpertResponse.class
);

ExpertResponse expert = response.getBody();
```

## Other Expert Endpoints

[List and document other expert endpoints here...] 