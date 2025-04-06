-- Add new assessment categories for pre-marriage counseling
INSERT INTO assessment_categories (name, description, weight, status) VALUES
('Family Planning', 'Evaluating alignment on family planning and parenting approaches', 1.0, 'ACTIVE'),
('Religious & Cultural Values', 'Assessing compatibility in religious beliefs and cultural practices', 1.0, 'ACTIVE'),
('Intimacy & Connection', 'Exploring physical and emotional intimacy expectations', 1.0, 'ACTIVE'),
('Premarital Education', 'General premarital education and preparation for marriage', 1.0, 'ACTIVE');

-- Create assessments for each new category
INSERT INTO assessments (category_id) VALUES
(4), -- Family Planning Assessment
(5), -- Religious & Cultural Values Assessment
(6), -- Intimacy & Connection Assessment
(7); -- Premarital Education Assessment

-- Add assessment questions for Family Planning
INSERT INTO assessment_questions (content, question_type, required, status, category_id) VALUES
-- Family Planning
('Do you and your partner agree on having children?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Have you discussed how many children you both want?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Do you agree on parenting styles and approaches?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Have you discussed childcare arrangements?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Do you agree on education approaches for your children?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Have you discussed family planning timing?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Are you aligned on work-family balance after having children?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Have you discussed roles and responsibilities in parenting?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Do you share similar values about raising children?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),
('Have you discussed how your extended families will be involved with your children?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 4),

-- Religious & Cultural Values
('How important is religion/spirituality in your relationship?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('Do you practice the same religion or belief system?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('Have you discussed how you will observe religious holidays?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('Do you agree on how religion will influence your children?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('How do you handle differences in cultural backgrounds?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('Have you met each other\'s extended families?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('Do you respect each other\'s cultural traditions?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('Have you discussed which cultural practices to maintain in your home?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('Are both families supportive of your relationship?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),
('Do you have a plan for navigating cultural differences?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 5),

-- Intimacy & Connection
('Are you satisfied with your physical intimacy?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('Do you openly discuss your intimacy needs?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('How often do you express affection outside the bedroom?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('Do you feel emotionally connected to your partner?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('Do you have similar views on physical intimacy in marriage?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('Can you discuss sensitive topics related to intimacy?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('Do you prioritize quality time together?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('How do you maintain connection during busy periods?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('Are your expectations about intimacy aligned?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),
('How do you plan to maintain intimacy through challenges?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 6),

-- Premarital Education
('Have you attended any premarital counseling or courses?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Do you understand the legal implications of marriage?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Have you discussed expectations about married life?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Do you have a clear vision for your future together?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Have you discussed living arrangements after marriage?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Are you prepared for the transition to married life?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Have you discussed wedding planning and budget?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Do you have shared goals for the first year of marriage?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Have you discussed prenuptial agreements?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7),
('Are you committed to ongoing relationship education?', 'MULTIPLE_CHOICE', TRUE, 'ACTIVE', 7);

-- Add question options for Family Planning
INSERT INTO assessment_question_options (question_id, option_text, weight) VALUES
-- Family Planning
(31, 'No, we disagree', 1.0), (31, 'We\'re still discussing', 2.0), (31, 'Yes, we agree', 3.0),
(32, 'No, we haven\'t discussed it', 1.0), (32, 'We\'ve mentioned it', 2.0), (32, 'Yes, we\'ve discussed it thoroughly', 3.0),
(33, 'No, we have different views', 1.0), (33, 'Somewhat aligned', 2.0), (33, 'Yes, fully aligned', 3.0),
(34, 'No, we haven\'t discussed it', 1.0), (34, 'We\'ve briefly discussed it', 2.0), (34, 'Yes, we have a plan', 3.0),
(35, 'No, we disagree', 1.0), (35, 'Somewhat aligned', 2.0), (35, 'Yes, fully aligned', 3.0),
(36, 'No, we haven\'t discussed it', 1.0), (36, 'We\'ve briefly discussed it', 2.0), (36, 'Yes, we have a timeline', 3.0),
(37, 'No, we haven\'t discussed it', 1.0), (37, 'We have different views', 2.0), (37, 'Yes, we\'re aligned', 3.0),
(38, 'No, we haven\'t discussed it', 1.0), (38, 'We\'ve briefly discussed it', 2.0), (38, 'Yes, we\'ve planned this', 3.0),
(39, 'No, we have different values', 1.0), (39, 'Somewhat aligned', 2.0), (39, 'Yes, fully aligned', 3.0),
(40, 'No, we haven\'t discussed it', 1.0), (40, 'We have different views', 2.0), (40, 'Yes, we\'re aligned', 3.0),

-- Religious & Cultural Values
(41, 'Not important', 1.0), (41, 'Somewhat important', 2.0), (41, 'Very important', 3.0),
(42, 'No, different beliefs', 1.0), (42, 'Similar but not same', 2.0), (42, 'Yes, same beliefs', 3.0),
(43, 'No, we haven\'t discussed it', 1.0), (43, 'We\'ve briefly discussed it', 2.0), (43, 'Yes, we have a plan', 3.0),
(44, 'No, we disagree', 1.0), (44, 'Somewhat aligned', 2.0), (44, 'Yes, fully aligned', 3.0),
(45, 'We struggle with differences', 1.0), (45, 'We manage differences', 2.0), (45, 'We celebrate differences', 3.0),
(46, 'No, not yet', 1.0), (46, 'Met some family members', 2.0), (46, 'Yes, met most/all family', 3.0),
(47, 'No, we struggle with this', 1.0), (47, 'Sometimes', 2.0), (47, 'Yes, always', 3.0),
(48, 'No, we haven\'t discussed it', 1.0), (48, 'We\'ve briefly discussed it', 2.0), (48, 'Yes, we have a plan', 3.0),
(49, 'No, they\'re not supportive', 1.0), (49, 'Some family members are', 2.0), (49, 'Yes, fully supportive', 3.0),
(50, 'No, we don\'t have a plan', 1.0), (50, 'We handle issues as they arise', 2.0), (50, 'Yes, we have strategies', 3.0),

-- Intimacy & Connection
(51, 'No, unsatisfied', 1.0), (51, 'Somewhat satisfied', 2.0), (51, 'Yes, very satisfied', 3.0),
(52, 'No, we avoid the topic', 1.0), (52, 'Sometimes', 2.0), (52, 'Yes, openly', 3.0),
(53, 'Rarely', 1.0), (53, 'Sometimes', 2.0), (53, 'Often', 3.0), (53, 'Daily', 4.0),
(54, 'No, disconnected', 1.0), (54, 'Somewhat connected', 2.0), (54, 'Yes, deeply connected', 3.0),
(55, 'No, different views', 1.0), (55, 'Somewhat aligned', 2.0), (55, 'Yes, fully aligned', 3.0),
(56, 'No, we avoid these topics', 1.0), (56, 'With difficulty', 2.0), (56, 'Yes, comfortably', 3.0),
(57, 'Rarely', 1.0), (57, 'Sometimes', 2.0), (57, 'Often', 3.0), (57, 'Always', 4.0),
(58, 'We lose connection', 1.0), (58, 'We try to stay connected', 2.0), (58, 'We have specific practices', 3.0),
(59, 'No, misaligned expectations', 1.0), (59, 'Somewhat aligned', 2.0), (59, 'Yes, fully aligned', 3.0),
(60, 'We haven\'t discussed this', 1.0), (60, 'We have some ideas', 2.0), (60, 'We have specific strategies', 3.0),

-- Premarital Education
(61, 'No', 1.0), (61, 'Planning to', 2.0), (61, 'Yes', 3.0),
(62, 'No, not really', 1.0), (62, 'Somewhat', 2.0), (62, 'Yes, fully', 3.0),
(63, 'No, we haven\'t discussed it', 1.0), (63, 'Somewhat', 2.0), (63, 'Yes, thoroughly', 3.0),
(64, 'No, unclear vision', 1.0), (64, 'Somewhat clear', 2.0), (64, 'Yes, very clear', 3.0),
(65, 'No, we haven\'t discussed it', 1.0), (65, 'Briefly discussed', 2.0), (65, 'Yes, detailed plan', 3.0),
(66, 'No, feeling unprepared', 1.0), (66, 'Somewhat prepared', 2.0), (66, 'Yes, well prepared', 3.0),
(67, 'No, we haven\'t discussed it', 1.0), (67, 'Started planning', 2.0), (67, 'Yes, detailed plan', 3.0),
(68, 'No, no specific goals', 1.0), (68, 'Some general ideas', 2.0), (68, 'Yes, specific goals', 3.0),
(69, 'No, we haven\'t discussed it', 1.0), (69, 'Briefly mentioned it', 2.0), (69, 'Yes, thoroughly discussed', 3.0),
(70, 'No, not interested', 1.0), (70, 'Somewhat interested', 2.0), (70, 'Yes, fully committed', 3.0);

-- Insert assessment-question mappings for new categories
INSERT INTO assessment_question_mapping (assessment_id, question_id) VALUES
-- Family Planning
(4, 31), (4, 32), (4, 33), (4, 34), (4, 35), (4, 36), (4, 37), (4, 38), (4, 39), (4, 40),
-- Religious & Cultural Values
(5, 41), (5, 42), (5, 43), (5, 44), (5, 45), (5, 46), (5, 47), (5, 48), (5, 49), (5, 50),
-- Intimacy & Connection
(6, 51), (6, 52), (6, 53), (6, 54), (6, 55), (6, 56), (6, 57), (6, 58), (6, 59), (6, 60),
-- Premarital Education
(7, 61), (7, 62), (7, 63), (7, 64), (7, 65), (7, 66), (7, 67), (7, 68), (7, 69), (7, 70);

-- Insert interpretations for new categories
INSERT INTO assessment_interpretations (min_score, max_score, interpretation, recommendation, category_id) VALUES
-- Family Planning
(0, 39, 'You have significant differences in family planning expectations.', 'Consider open discussions about your desires for children and parenting approaches. A family counselor could help mediate these conversations.', 4),
(30, 50, 'You have some alignment on family planning, but key areas need discussion.', 'Create time to discuss specific aspects of family planning where you differ, such as timing, number of children, or parenting philosophies.', 4),
(50, 70, 'You have good alignment on family planning with a few areas to address.', 'Continue your open communication about family planning and consider reading parenting books together.', 4),
(60, 80, 'You have strong alignment on family planning and parenting.', 'Maintain your communication about family planning as your relationship evolves.', 4),
(75, 100, 'You have excellent alignment on family planning and parenting approaches.', 'Your shared vision for family will serve as a strong foundation. Consider periodic check-ins as life circumstances change.', 4),

-- Religious & Cultural Values
(0, 39, 'You have significant differences in religious and cultural values.', 'Consider exploring each other\'s traditions more deeply and discussing how to honor both in your relationship.', 5),
(30, 50, 'You respect each other\'s values but may need more alignment on practice.', 'Discuss which practices are most important to each of you and how to incorporate them into your shared life.', 5),
(50, 70, 'You have good alignment on religious and cultural values.', 'Continue exploring ways to blend your values and traditions meaningfully.', 5),
(60, 80, 'You have strong alignment on religious and cultural values.', 'Your respect for each other\'s backgrounds will strengthen your relationship; continue open dialogue about evolving beliefs.', 5),
(75, 100, 'You have excellent alignment on religious and cultural values.', 'Your shared understanding of cultural and spiritual matters will serve as a strong foundation for your relationship.', 5),

-- Intimacy & Connection
(0, 39, 'You may be experiencing challenges in physical and emotional intimacy.', 'Consider working with a relationship counselor to improve communication about intimacy needs and expectations.', 6),
(30, 50, 'Your intimacy and connection have some strong points but need attention.', 'Set aside regular time to connect and discuss your intimacy needs openly.', 6),
(50, 70, 'You have a good foundation of intimacy and connection.', 'Continue nurturing your connection through regular quality time and open communication about needs.', 6),
(60, 80, 'You have a strong intimate connection.', 'Your connection is healthy; continue prioritizing time together and communicating openly.', 6),
(75, 100, 'You have an excellent intimate connection with strong communication.', 'Your intimate connection is a strength in your relationship; maintain your openness and intentionality.', 6),

-- Premarital Education
(0, 39, 'You would benefit from comprehensive premarital education.', 'Consider enrolling in a structured premarital course or seeking counseling to prepare for marriage.', 7),
(30, 50, 'You have some preparation for marriage but would benefit from more education.', 'Explore specific premarital resources addressing areas where you feel less prepared.', 7),
(50, 70, 'You have a good foundation of premarital knowledge.', 'Continue seeking resources and discussions to strengthen your preparation for marriage.', 7),
(60, 80, 'You are well-prepared for the transition to marriage.', 'Your preparation will serve you well; consider focusing on creating specific goals for your first year.', 7),
(75, 100, 'You have excellent preparation and shared understanding of marriage.', 'Your thorough preparation indicates a strong commitment to a successful marriage; continue the open dialogue as you move forward.', 7);