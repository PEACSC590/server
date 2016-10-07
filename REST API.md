# REST API Documentation

**POST** Authentication
`/login`

Input: Body
{
  "login": "...",
  "password": "..."
}

Output: JSON
{
  "valid": [boolean]
}