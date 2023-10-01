import pandas as pd

import os
import numpy as np
import pandas as pd
import spacy

# Define the path to the file where the dataset is cached
cache_file = 'cached_dataset.pkl'

# Define a function to load the CSV data or read from cache if available
def load_csv_data():
    if os.path.exists(cache_file):
        # If the cache file exists, load the dataset from cache
        return pd.read_pickle(cache_file)
    else:
        # Get the absolute path to the CSV file using double backslashes
        csv_file_path = os.path.abspath('D:\\IUT\\demo\\src\\main\\resources\\drugs_side_effects.csv')


        # Load the CSV file using the absolute path
        loaded_dataset = pd.read_csv(csv_file_path)

        # Specify the value(s) you want to remove from 'activity'
        values_to_remove = ['0%']

        # Remove rows with specified values in 'activity'
        loaded_dataset = loaded_dataset[~loaded_dataset['activity'].isin(values_to_remove)]

        # Save the dataset to cache for future use
        loaded_dataset.to_pickle(cache_file)

        return loaded_dataset

# Call the function to load the CSV data once or read from cache if available
dataset = load_csv_data()

dataset['generic_name'] = dataset['generic_name'].str.lower()
dataset['medical_condition'] = dataset['medical_condition'].str.lower()
dataset['side_effects'] = dataset['side_effects'].str.lower()
dataset['drug_name'] = dataset['drug_name'].str.lower()

dataset['side_effects'] = dataset['side_effects'].astype(str)

user_input = input("")
user_input = user_input.lower()



# Load the spaCy English model
nlp = spacy.load("en_core_web_sm")

# Define a function to calculate the cosine similarity between two sentences
def calculate_cosine_similarity(sentence1, sentence2):
    # Tokenize the sentences
    tokens1 = nlp(sentence1)
    tokens2 = nlp(sentence2)

    # Calculate the sentence embeddings
    sentence_embedding1 = tokens1.vector
    sentence_embedding2 = tokens2.vector

    # Calculate the cosine similarity
    cosine_similarity = np.dot(sentence_embedding1, sentence_embedding2) / (
        np.linalg.norm(sentence_embedding1) * np.linalg.norm(sentence_embedding2)
    )

    return cosine_similarity

ans = []
sim_score = []
activity = []

for i in range(len(dataset)):
    sentence1 = dataset['side_effects'][i]
    sentence2 = user_input
    cosine_similarity = calculate_cosine_similarity(sentence1, sentence2)
    if cosine_similarity > 0.5:
        ans.append(dataset['generic_name'][i])
        sim_score.append(cosine_similarity)
        activity.append(dataset['activity'][i])

results = []

for i in range(len(ans)):
     formatted_similarity = "{:.2f}".format(sim_score[i])
     drug_name = ans[i]
     activity_value = activity[i]
     if drug_name is not None and activity_value is not None:
         results.append([drug_name, formatted_similarity, activity_value])

header = "<b>drug,probability,activity</b>"

# Create the result lines, excluding lines with "nan" values
lines = [f"{r[0]},{r[1]},{r[2]}" for r in results if "nan" not in r]

# Combine the header and result lines with newline characters
output_csv = header + "\n" + "\n".join(lines) + "\n"
print(output_csv)