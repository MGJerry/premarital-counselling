-- Clear existing data from assessment categories table to avoid conflicts
DELETE FROM assessment_questions WHERE category_id > 0;
DELETE FROM assessment_interpretations WHERE category_id > 0;
DELETE FROM assessments WHERE category_id > 0;
DELETE FROM assessment_categories WHERE category_id > 0;

-- Reset auto-increment for the table
ALTER TABLE assessment_categories AUTO_INCREMENT = 1;

-- Insert updated assessment categories with proper field names and pre-marriage counseling focus
INSERT INTO assessment_categories (name, description, status, weight) VALUES
('Communication', 'Assessing communication effectiveness between partners in pre-marriage counseling', 'ACTIVE', 1.0),
('Financial Compatibility', 'Understanding financial compatibility and planning between partners before marriage', 'ACTIVE', 1.0),
('Conflict Resolution', 'Evaluating how couples handle conflicts and disagreements before marriage', 'ACTIVE', 1.0),
('Family Planning', 'Assessing alignment on family planning and parenting approaches for future marriage', 'ACTIVE', 1.0),
('Religious & Cultural Values', 'Understanding compatibility in religious beliefs and cultural practices between partners', 'ACTIVE', 1.0),
('Intimacy & Connection', 'Exploring physical and emotional intimacy expectations in pre-marriage counseling', 'ACTIVE', 1.0),
('Premarital Education', 'General premarital education and preparation for successful marriage', 'ACTIVE', 1.0);

-- Display the updated table
SELECT * FROM assessment_categories;