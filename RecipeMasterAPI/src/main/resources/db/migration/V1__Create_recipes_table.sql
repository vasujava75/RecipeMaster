CREATE TABLE recipes (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         instructions TEXT NOT NULL,
                         servings INT NOT NULL,
                         vegetarian BOOLEAN NOT NULL DEFAULT FALSE,
                         description VARCHAR(1000),
                         prep_time_minutes INT,
                         cook_time_minutes INT,
                         difficulty VARCHAR(20),
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                         CONSTRAINT chk_servings_positive CHECK (servings > 0),
                         CONSTRAINT chk_prep_time_positive CHECK (prep_time_minutes IS NULL OR prep_time_minutes >= 0),
                         CONSTRAINT chk_cook_time_positive CHECK (cook_time_minutes IS NULL OR cook_time_minutes >= 0),
                         CONSTRAINT chk_difficulty CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD'))
);

CREATE INDEX idx_recipes_vegetarian ON recipes(vegetarian);
CREATE INDEX idx_recipes_servings ON recipes(servings);
CREATE INDEX idx_recipes_difficulty ON recipes(difficulty);
