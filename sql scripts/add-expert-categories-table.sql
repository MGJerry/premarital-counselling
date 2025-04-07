-- Create the expert_categories join table if it doesn't exist
CREATE TABLE IF NOT EXISTS expert_categories (
  expert_id BIGINT NOT NULL,
  category_id BIGINT NOT NULL,
  PRIMARY KEY (expert_id, category_id),
  FOREIGN KEY (expert_id) REFERENCES experts (id) ON DELETE CASCADE,
  FOREIGN KEY (category_id) REFERENCES assessment_categories (category_id) ON DELETE CASCADE
);

-- Add an index for better performance
CREATE INDEX IF NOT EXISTS idx_expert_categories_category ON expert_categories (category_id);