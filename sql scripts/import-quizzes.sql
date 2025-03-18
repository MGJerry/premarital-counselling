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
('Do you and your partner discuss major purchases before making them?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),
('How would you handle it if your partner had a large debt?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 2),
('Do you and your partner have similar attitudes towards saving money?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),
('How do you plan to split expenses in the relationship?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 2),
('Do you and your partner invest in your future together (e.g., savings, retirement, property)?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 2),
('Have you and your partner discussed financial goals for the next five years?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 2);


-- Conflict Resolution
('How do you handle disagreements in your relationship?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3),
('Do you feel comfortable apologizing when you are wrong?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 3),
('How often do conflicts escalate into major arguments?', 'MULTIPLE_CHOICE', 0, 1.0, TRUE, 'ACTIVE', 3),
('Do you and your partner take breaks during heated arguments?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3),
('How do you handle criticism from your partner?', 'MULTIPLE_CHOICE', 1, 1.0, TRUE, 'ACTIVE', 3),
('Do you and your partner revisit past conflicts to find better solutions?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 3),
('How comfortable are you expressing your needs in a conflict?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3),
('Do you and your partner forgive each other easily?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3),
('How often do you and your partner compromise?', 'MULTIPLE_CHOICE', 3, 1.0, TRUE, 'ACTIVE', 3),
('Do you and your partner use humor to diffuse conflicts?', 'MULTIPLE_CHOICE', 2, 1.0, TRUE, 'ACTIVE', 3);

-- Insert Assessment Question Options
INSERT INTO assessment_question_options (assessment_question_question_id, options) VALUES
-- Communication
(1, 'Rarely'), (1, 'Sometimes'), (1, 'Often'), (1, 'Always'),
(2, 'Not comfortable at all'), (2, 'Somewhat comfortable'), (2, 'Very comfortable'),
(3, 'Avoid conflict'), (3, 'Talk it out calmly'), (3, 'Argue until one wins'),
(4, 'Never'), (4, 'Sometimes'), (4, 'Always'),
(5, 'Rarely'), (5, 'Sometimes'), (5, 'Often'), (5, 'Always'),
(6, 'Ignore it'), (6, 'Talk about it'), (6, 'Get defensive'),
(7, 'Never'), (7, 'Sometimes'), (7, 'Often'),
(8, 'No'), (8, 'Sometimes'), (8, 'Yes'),
(9, 'Not at all'), (9, 'Somewhat'), (9, 'Completely'),
(10, 'Never'), (10, 'Sometimes'), (10, 'Often'), (10, 'Always'),

-- Financial Compatibility
(11, 'No, we don’t have a budget'), (11, 'Yes, we have a budget'),
(12, 'We argue about money'), (12, 'We compromise'), (12, 'We avoid talking about money'),
(13, 'Not at all'), (13, 'Somewhat'), (13, 'Completely'),
(14, 'Rarely'), (14, 'Sometimes'), (14, 'Often'), (14, 'Always'),
(15, 'No, we make decisions individually'), (15, 'Sometimes'), (15, 'Yes, always together'),
(16, 'I would help them plan repayment'), (16, 'I would feel uncomfortable'), (16, 'I would ignore it'),
(17, 'Not at all'), (17, 'Somewhat'), (17, 'Completely aligned'),
(18, 'We split everything 50/50'), (18, 'We contribute based on income'), (18, 'One partner pays more'),
(19, 'No, we don’t'), (19, 'Sometimes'), (19, 'Yes, we do'),
(20, 'No, we have not discussed it'), (20, 'We’ve mentioned it'), (20, 'Yes, we have a clear plan'),

-- Conflict Resolution
(21, 'We ignore it'), (21, 'We talk about it'), (21, 'We argue'),
(22, 'No, I avoid apologizing'), (22, 'Yes, I apologize when wrong'),
(23, 'Never'), (23, 'Sometimes'), (23, 'Often'), (23, 'Always');
(24, 'No, we keep arguing'), (24, 'Sometimes'), (24, 'Yes, we take breaks'),
(25, 'I take it personally'), (25, 'I listen and respond calmly'), (25, 'I avoid the topic'),
(26, 'Never'), (26, 'Sometimes'), (26, 'Often'),
(27, 'Not comfortable'), (27, 'Somewhat comfortable'), (27, 'Very comfortable'),
(28, 'No, we hold grudges'), (28, 'Sometimes'), (28, 'Yes, we forgive quickly'),
(29, 'Rarely'), (29, 'Sometimes'), (29, 'Often'),
(30, 'No, we argue seriously'), (30, 'Sometimes'), (30, 'Yes, humor helps us reconnect');

-- Insert Assessment and Question Mappings
INSERT INTO assessment_question_mapping (assessment_id, question_id) VALUES
-- Communication
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
-- Financial Compatibility
(2, 11), (2, 12), (2, 13), (2, 14), (2, 15), (2, 16), (2, 17), (2, 18), (2, 19), (2, 20),
-- Conflict Resolution
(3, 21), (3, 22), (3, 23), (3, 24), (3, 25), (3, 26), (3, 27), (3, 28), (3, 29), (3, 30);
