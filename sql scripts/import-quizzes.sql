-- Insert Assessment Categories
INSERT INTO assessment_categories (name, description, weight, status) VALUES
('Communication', 'Assessing communication effectiveness in a relationship', 1.0, 'ACTIVE'),
('Financial Compatibility', 'Understanding financial compatibility between partners', 1.0, 'ACTIVE'),
('Conflict Resolution', 'Evaluating how couples handle conflicts', 1.0, 'ACTIVE');

-- Insert Assessments
INSERT INTO assessments (category_id) VALUES
(1), -- Communication Assessment
(2), -- Financial Compatibility Assessment
(3); -- Conflict Resolution Assessment

-- Insert Assessment Questions
INSERT INTO assessment_questions (content, question_type, answer, weight, required, status, category_id) VALUES
-- Communication
('How often do you and your partner discuss your future together?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 1),
('How comfortable are you expressing your emotions to your partner?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 1),
('How do you handle conflicts in your relationship?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 1),
('Do you feel heard and understood in your relationship?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 1),
('How often do you talk about each other’s feelings?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 1),
('What do you do when your partner says something that hurts you?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 1),
('How often do you and your partner talk about difficult topics?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 1),
('Do you feel comfortable bringing up concerns with your partner?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 1),
('How well do you understand your partner’s love language?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 1),
('How often do you express appreciation for your partner?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 1),

-- Financial Compatibility
('Do you and your partner have a budget?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 2),
('How do you handle financial disagreements?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),
('Do you and your partner share financial goals?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),
('How often do you discuss money matters?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 2),
('Who is responsible for managing finances in your relationship?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),
('Do you feel financially secure with your partner?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),
('How do you handle unexpected financial expenses?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 2),
('Do you and your partner save money together?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),
('Have you discussed debt and credit scores with your partner?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 2),
('Do you feel financially equal in the relationship?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),

-- Conflict Resolution
('How do you handle disagreements in your relationship?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3),
('Do you feel comfortable apologizing when you are wrong?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 3),
('How often do conflicts escalate into major arguments?', 'MULTIPLE_CHOICE', 0, 1.0, TRUE, 'ACTIVE', 3),
('Do you and your partner resolve issues quickly?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3),
('How do you handle misunderstandings?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 3),
('What do you do when you feel frustrated with your partner?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3),
('How often do you discuss past conflicts to improve your relationship?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 3),
('Do you and your partner give each other space after arguments?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3),
('Do you think conflict makes your relationship stronger?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 3),
('Do you and your partner seek compromise during conflicts?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3);

-- Insert Assessment Question Options
INSERT INTO assessment_question_options (assessment_question_question_id, options) VALUES
-- Communication
(1, 'Rarely'), (1, 'Sometimes'), (1, 'Often'), (1, 'Always'),
(2, 'Not comfortable at all'), (2, 'Somewhat comfortable'), (2, 'Very comfortable'),
(3, 'Avoid conflict'), (3, 'Talk it out calmly'), (3, 'Argue until one wins'),
(4, 'Never'), (4, 'Sometimes'), (4, 'Always'),
(5, 'Rarely'), (5, 'Sometimes'), (5, 'Often'), (5, 'Always'),

-- Financial Compatibility
(11, 'No, we don’t have a budget'), (11, 'Yes, we have a budget'),
(12, 'We argue about money'), (12, 'We compromise'), (12, 'We avoid talking about money'),
(13, 'Not at all'), (13, 'Somewhat'), (13, 'Completely'),
(14, 'Rarely'), (14, 'Sometimes'), (14, 'Often'), (14, 'Always'),

-- Conflict Resolution
(21, 'We ignore it'), (21, 'We talk about it'), (21, 'We argue'),
(22, 'No, I avoid apologizing'), (22, 'Yes, I apologize when wrong'),
(23, 'Never'), (23, 'Sometimes'), (23, 'Often'), (23, 'Always');

-- Insert Assessment and Question Mappings
INSERT INTO assessment_question_mapping (assessment_id, question_id) VALUES
-- Communication
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
-- Financial Compatibility
(2, 11), (2, 12), (2, 13), (2, 14), (2, 15), (2, 16), (2, 17), (2, 18), (2, 19), (2, 20),
-- Conflict Resolution
(3, 21), (3, 22), (3, 23), (3, 24), (3, 25), (3, 26), (3, 27), (3, 28), (3, 29), (3, 30);
