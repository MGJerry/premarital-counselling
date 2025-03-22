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
INSERT INTO assessment_questions (content, question_type, required, status, category_id) VALUES
-- Communication
('How often do you and your partner discuss your future together?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('How comfortable are you expressing your emotions to your partner?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('How do you handle conflicts in your relationship?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('Do you feel heard and understood in your relationship?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('How often do you talk about each other’s feelings?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('What do you do when your partner says something that hurts you?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('How often do you and your partner talk about difficult topics?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('Do you feel comfortable bringing up concerns with your partner?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('How well do you understand your partner’s love language?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),
('How often do you express appreciation for your partner?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 1),

-- Financial Compatibility
('Do you and your partner have a budget?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('How do you handle financial disagreements?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('Do you and your partner share financial goals?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('How often do you discuss money matters?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('Do you and your partner discuss major purchases before making them?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('How would you handle it if your partner had a large debt?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('Do you and your partner have similar attitudes towards saving money?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('How do you plan to split expenses in the relationship?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('Do you and your partner invest in your future together (e.g., savings, retirement, property)?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),
('Have you and your partner discussed financial goals for the next five years?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 2),

-- Conflict Resolution
('How do you handle disagreements in your relationship?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('Do you feel comfortable apologizing when you are wrong?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('How often do conflicts escalate into major arguments?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('Do you and your partner take breaks during heated arguments?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('How do you handle criticism from your partner?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('Do you and your partner revisit past conflicts to find better solutions?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('How comfortable are you expressing your needs in a conflict?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('Do you and your partner forgive each other easily?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('How often do you and your partner compromise?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3),
('Do you and your partner use humor to diffuse conflicts?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 3);

-- Insert Assessment Question Options
INSERT INTO assessment_question_options (assessment_question_question_id, options) VALUES
-- Communication
(1, 'Rarely', 1.0), (1, 'Sometimes', 2.0), (1, 'Often', 3.0), (1, 'Always', 4.0),
(2, 'Not comfortable at all', 1.0), (2, 'Somewhat comfortable', 2.0), (2, 'Very comfortable', 3.0),
(3, 'Avoid conflict', 1.0), (3, 'Talk it out calmly', 2.0), (3, 'Argue until one wins', 3.0),
(4, 'Never', 1.0), (4, 'Sometimes', 2.0), (4, 'Often', 3.0), (4, 'Always', 4.0),
(5, 'Rarely', 1.0), (5, 'Sometimes', 2.0), (5, 'Often', 3.0), (5, 'Always', 4.0),
(6, 'Ignore it', 1.0), (6, 'Talk about it', 2.0), (6, 'Get defensive', 3.0),
(7, 'Never', 1.0), (7, 'Sometimes', 2.0), (7, 'Often', 3.0),
(8, 'No', 1.0), (8, 'Sometimes', 2.0), (8, 'Yes', 3.0),
(9, 'Not at all', 1.0), (9, 'Somewhat', 2.0), (9, 'Completely', 3.0),
(10, 'Never', 1.0), (10, 'Sometimes', 2.0), (10, 'Often', 3.0), (10, 'Always', 4.0),

-- Financial Compatibility
(11, 'No, we don’t have a budget', 1.0), (11, 'Yes, we have a budget', 2.0),
(12, 'We argue about money', 1.0), (12, 'We compromise'), (12, 'We avoid talking about money', 2.0),
(13, 'Not at all', 1.0), (13, 'Somewhat', 2.0), (13, 'Completely', 3.0),
(14, 'Rarely', 1.0), (14, 'Sometimes', 2.0), (14, 'Often', 3.0), (14, 'Always', 4.0),
(15, 'No, we make decisions individually', 1.0), (15, 'Sometimes', 2.0), (15, 'Yes, always together', 3.0),
(16, 'I would help them plan repayment', 1.0), (16, 'I would feel uncomfortable', 2.0), (16, 'I would ignore it', 3.0),
(17, 'Not at all', 1.0), (17, 'Somewhat', 2.0), (17, 'Completely aligned', 3.0),
(18, 'We split everything 50/50', 1.0), (18, 'We contribute based on income', 2.0), (18, 'One partner pays more', 3.0),
(19, 'No, we don’t', 1.0), (19, 'Sometimes', 2.0), (19, 'Yes, we do', 3.0),
(20, 'No, we have not discussed it', 1.0), (20, 'We’ve mentioned it', 2.0), (20, 'Yes, we have a clear plan', 3.0),

-- Conflict Resolution
(21, 'We ignore it', 1.0), (21, 'We talk about it', 2.0), (21, 'We argue', 3.0),
(22, 'No, I avoid apologizing', 1.0), (22, 'Yes, I apologize when wrong', 2.0),
(23, 'Never', 1.0), (23, 'Sometimes', 2.0), (23, 'Often', 3.0), (23, 'Always', 4.0);
(24, 'No, we keep arguing', 1.0), (24, 'Sometimes', 2.0), (24, 'Yes, we take breaks', 3.0),
(25, 'I take it personally', 1.0), (25, 'I listen and respond calmly', 2.0), (25, 'I avoid the topic', 3.0),
(26, 'Never', 1.0), (26, 'Sometimes', 2.0), (26, 'Often', 3.0),
(27, 'Not comfortable', 1.0), (27, 'Somewhat comfortable', 2.0), (27, 'Very comfortable', 3.0),
(28, 'No, we hold grudges', 1.0), (28, 'Sometimes', 2.0), (28, 'Yes, we forgive quickly', 3.0),
(29, 'Rarely', 1.0), (29, 'Sometimes', 2.0), (29, 'Often', 3.0),
(30, 'No, we argue seriously', 1.0), (30, 'Sometimes', 2.0), (30, 'Yes, humor helps us reconnect', 3.0);

-- Insert Assessment and Question Mappings
INSERT INTO assessment_question_mapping (assessment_id, question_id) VALUES
-- Communication
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10),
-- Financial Compatibility
(2, 11), (2, 12), (2, 13), (2, 14), (2, 15), (2, 16), (2, 17), (2, 18), (2, 19), (2, 20),
-- Conflict Resolution
(3, 21), (3, 22), (3, 23), (3, 24), (3, 25), (3, 26), (3, 27), (3, 28), (3, 29), (3, 30);
