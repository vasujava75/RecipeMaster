-- Insert sample recipes
INSERT INTO recipes (name, description, instructions, servings, created_at, updated_at) VALUES
                                                                                            ('Spaghetti Carbonara', 'Classic Italian pasta dish with eggs, cheese, and pancetta',
                                                                                             '1. Cook spaghetti according to package directions
                                                                                              2. Fry pancetta until crispy
                                                                                              3. Beat eggs with cheese
                                                                                              4. Combine hot pasta with pancetta
                                                                                              5. Add egg mixture and toss quickly
                                                                                              6. Season with pepper and serve immediately',
                                                                                             4, NOW(), NOW()),

                                                                                            ('Chicken Caesar Salad', 'Fresh romaine lettuce with grilled chicken, croutons, and Caesar dressing',
                                                                                             '1. Grill chicken breast and slice
                                                                                              2. Wash and chop romaine lettuce
                                                                                              3. Make croutons from bread cubes
                                                                                              4. Prepare Caesar dressing
                                                                                              5. Toss everything together
                                                                                              6. Top with parmesan cheese',
                                                                                             2, NOW(), NOW()),

                                                                                            ('Chocolate Chip Cookies', 'Soft and chewy homemade chocolate chip cookies',
                                                                                             '1. Cream butter and sugars
                                                                                              2. Add eggs and vanilla
                                                                                              3. Mix in flour, baking soda, and salt
                                                                                              4. Fold in chocolate chips
                                                                                              5. Drop onto baking sheets
                                                                                              6. Bake at 375°F for 9-11 minutes',
                                                                                             24, NOW(), NOW());

-- Insert sample ingredients for Spaghetti Carbonara (recipe_id = 1)
INSERT INTO ingredients (name, quantity, unit, recipe_id, notes) VALUES
                                                                     ('Spaghetti', 400, 'grams', 1, 'Use good quality pasta'),
                                                                     ('Pancetta', 150, 'grams', 1, 'Diced'),
                                                                     ('Large Eggs', 3, 'pieces', 1, 'Room temperature'),
                                                                     ('Pecorino Romano', 100, 'grams', 1, 'Freshly grated'),
                                                                     ('Black Pepper', 1, 'tsp', 1, 'Freshly ground'),
                                                                     ('Olive Oil', 2, 'tbsp', 1, 'Extra virgin');

-- Insert sample ingredients for Chicken Caesar Salad (recipe_id = 2)
INSERT INTO ingredients (name, quantity, unit, recipe_id, notes) VALUES
                                                                     ('Chicken Breast', 2, 'pieces', 2, 'Boneless, skinless'),
                                                                     ('Romaine Lettuce', 1, 'head', 2, 'Fresh and crisp'),
                                                                     ('Bread', 4, 'slices', 2, 'For croutons'),
                                                                     ('Parmesan Cheese', 50, 'grams', 2, 'Freshly grated'),
                                                                     ('Caesar Dressing', 4, 'tbsp', 2, 'Store-bought or homemade'),
                                                                     ('Garlic', 2, 'cloves', 2, 'For seasoning');

-- Insert sample ingredients for Chocolate Chip Cookies (recipe_id = 3)
INSERT INTO ingredients (name, quantity, unit, recipe_id, notes) VALUES
                                                                     ('All-Purpose Flour', 300, 'grams', 3, 'Sifted'),
                                                                     ('Butter', 200, 'grams', 3, 'Softened'),
                                                                     ('Brown Sugar', 150, 'grams', 3, 'Packed'),
                                                                     ('White Sugar', 100, 'grams', 3, 'Granulated'),
                                                                     ('Large Eggs', 2, 'pieces', 3, 'Room temperature'),
                                                                     ('Vanilla Extract', 2, 'tsp', 3, 'Pure vanilla'),
                                                                     ('Baking Soda', 1, 'tsp', 3, NULL),
                                                                     ('Salt', 0.5, 'tsp', 3, NULL),
                                                                     ('Chocolate Chips', 200, 'grams', 3, 'Semi-sweet');
