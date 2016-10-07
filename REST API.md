# REST API Documentation

**POST** Authentication
`/login`

Input: Body
```json
{
  "login": "...",
  "password": "..."
}
```

Output: JSON
```json
{
  "valid": [boolean]
}
```