-- First, ensure we're starting fresh with the specializations table
DELETE FROM specializations WHERE id > 0;
ALTER TABLE specializations AUTO_INCREMENT = 1;

-- Insert specializations for each assessment category
-- Format: INSERT INTO specializations (category_id, name, description) VALUES (category_id, 'Specialization Name', 'Description')

/* Communication (category_id: 1) */
INSERT INTO specializations (category_id, name, description) VALUES
(1, 'Communication Skills Coaching', 'Helping couples develop effective verbal and non-verbal communication techniques'),
(1, 'Active Listening Training', 'Teaching partners how to truly hear and understand each other'),
(1, 'Emotional Expression Counseling', 'Guiding couples in expressing feelings constructively and empathetically'),
(1, 'Difficult Conversations Mediation', 'Facilitating challenging discussions between partners on sensitive topics');

/* Financial Compatibility (category_id: 2) */
INSERT INTO specializations (category_id, name, description) VALUES
(2, 'Financial Planning for Couples', 'Helping partners create joint financial goals and plans'),
(2, 'Money Management Counseling', 'Addressing differences in spending and saving behaviors'),
(2, 'Debt Resolution Strategies', 'Working with couples to develop plans for managing and eliminating debt'),
(2, 'Financial Transparency Coaching', 'Building trust through open financial communication');

/* Conflict Resolution (category_id: 3) */
INSERT INTO specializations (category_id, name, description) VALUES
(3, 'Dispute Resolution Techniques', 'Teaching methods to resolve disagreements constructively'),
(3, 'Anger Management for Couples', 'Helping partners manage emotions during conflicts'),
(3, 'Forgiveness and Healing Counseling', 'Guiding couples through past hurts and building reconciliation'),
(3, 'Negotiation and Compromise Skills', 'Developing strategies for finding middle ground and mutual benefit');

/* Family Planning (category_id: 4) */
INSERT INTO specializations (category_id, name, description) VALUES
(4, 'Family Planning Counseling', 'Discussing and aligning visions for future family development'),
(4, 'Parenting Styles Alignment', 'Helping partners develop compatible approaches to parenting'),
(4, 'Blended Family Integration', 'Supporting couples in merging existing families successfully'),
(4, 'Childcare Strategy Development', 'Planning balanced childcare responsibilities and approaches');

/* Religious & Cultural Values (category_id: 5) */
INSERT INTO specializations (category_id, name, description) VALUES
(5, 'Interfaith Relationship Counseling', 'Supporting couples with different religious backgrounds'),
(5, 'Cultural Traditions Integration', 'Helping partners blend diverse cultural practices and expectations'),
(5, 'Religious Ceremony Planning', 'Assisting with the integration of religious elements into wedding ceremonies'),
(5, 'Extended Family Dynamics', 'Navigating relationships with in-laws and family members from different backgrounds');

/* Intimacy & Connection (category_id: 6) */
INSERT INTO specializations (category_id, name, description) VALUES
(6, 'Physical Intimacy Counseling', 'Supporting couples in developing healthy physical relationships'),
(6, 'Emotional Bond Strengthening', 'Building deeper emotional connection between partners'),
(6, 'Rekindling Romance Coaching', 'Strategies for maintaining chemistry and excitement in long-term relationships'),
(6, 'Love Language Alignment', 'Identifying and responding to each partner\'s preferred expressions of love');

/* Premarital Education (category_id: 7) */
INSERT INTO specializations (category_id, name, description) VALUES
(7, 'Marriage Preparation Workshops', 'Comprehensive programs covering key aspects of marital readiness'),
(7, 'Expectation Setting Facilitation', 'Clarifying and aligning expectations about married life'),
(7, 'Relationship Strength Assessment', 'Evaluating and building upon existing relationship strengths'),
(7, 'Long-Term Commitment Counseling', 'Preparing couples for the realities of lifelong partnership commitment');

-- Display the inserted data
SELECT * FROM specializations;