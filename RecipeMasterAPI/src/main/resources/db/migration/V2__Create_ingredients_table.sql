CREATE TABLE ingredients (
                             id BIGSERIAL PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             quantity DOUBLE PRECISION NOT NULL,
                             unit VARCHAR(50) NOT NULL,
                             recipe_id BIGINT NOT NULL,
                             notes VARCHAR(500),

                             CONSTRAINT chk_quantity_positive CHECK (quantity > 0),
                             CONSTRAINT fk_ingredients_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id) ON DELETE CASCADE
);

CREATE INDEX idx_ingredients_recipe_id ON ingredients(recipe_id);
CREATE INDEX idx_ingredients_name ON ingredients(name);
