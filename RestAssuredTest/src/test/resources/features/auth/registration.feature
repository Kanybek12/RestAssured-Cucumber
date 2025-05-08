@registration
Scenario: Register user with JSON file data
Given I have registration data from file "testdata/auth.json"
When I submit the registration request
Then I should receive a successful response