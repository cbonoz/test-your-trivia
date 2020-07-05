import json
categories = [
'Art',
'History',
'Politics',
'Celebrities',
'Animal',
'Vehicles',
'Geography',
'Sports',
'Mythology',
'General'
]

create_type_value = lambda x: {'name': {'value': f"{x}"}}


category_json = list(map(create_type_value, categories))

with open('categories.json', 'w') as f:
    f.write(json.dumps(category_json))


example_api_response = {"response_code":0,"results":[{"category":"History","type":"multiple","difficulty":"medium","question":"What disease crippled President Franklin D. Roosevelt and led him to help the nation find a cure? ","correct_answer":"Polio","incorrect_answers":["Cancer","Meningitis","HIV"]},{"category":"History","type":"multiple","difficulty":"medium","question":"In what year was the video game company Electronic Arts founded?","correct_answer":"1982","incorrect_answers":["1999","1981","2005"]},{"category":"History","type":"multiple","difficulty":"medium","question":"What year were the Marian Reforms instituted in the Roman Republic?","correct_answer":"107 BCE","incorrect_answers":["42 BCE","264 BCE","102 CE"]},{"category":"History","type":"multiple","difficulty":"medium","question":"What is the bloodiest event in United States history, in terms of casualties?","correct_answer":"Battle of Antietam","incorrect_answers":["Pearl Harbor","September 11th","D-Day"]},{"category":"History","type":"multiple","difficulty":"medium","question":"What was the bloodiest single-day battle during the American Civil War?","correct_answer":"The Battle of Antietam","incorrect_answers":["The Siege of Vicksburg","The Battle of Gettysburg","The Battles of Chancellorsville"]},{"category":"History","type":"multiple","difficulty":"medium","question":"Which U.S. president took part in the Potsdam Conference, where the Allies reached a peace settlement with Germany?","correct_answer":"Harry S. Truman","incorrect_answers":["Dwight D. Eisenhower","Franklin D. Roosevelt","Herbert Hoover"]},{"category":"History","type":"multiple","difficulty":"medium","question":"What was the name of one of the surviving palaces of Henry VIII located near Richmond, London?","correct_answer":"Hampton Court","incorrect_answers":["St James&#039;s Palace","Buckingham Palace","Coughton Court"]},{"category":"History","type":"multiple","difficulty":"medium","question":"When was the Grand Patriotic War in the USSR concluded?","correct_answer":"May 9th, 1945","incorrect_answers":["September 2nd, 1945","August 9th, 1945","December 11th, 1945"]},{"category":"History","type":"multiple","difficulty":"medium","question":"Which of the following Presidents of the United States was assassinated?","correct_answer":"William McKinley","incorrect_answers":["Lyndon Johnson","Chester Arthur","Franklin Roosevelt"]},{"category":"History","type":"multiple","difficulty":"medium","question":"Who was the first Chancellor of a united Germany in 1871? ","correct_answer":"Otto Von Bismark","incorrect_answers":["Kaiser Wilhelm ","Fredrick the 2nd","Robert Koch"]}]}

example_answers = []
for r in example_api_response['results']:
    answers = [r['correct_answer'], *r['incorrect_answers']]
    example_answers.extend(answers)

example_answers = list(map(create_type_value, example_answers))

with open('example_answers.json', 'w') as f:
    f.write(json.dumps(example_answers))