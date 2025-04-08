-- First, update any invalid or NULL values to a valid state
UPDATE users SET e_status = 'PENDING' WHERE e_status IS NULL OR e_status NOT IN ('PENDING', 'APPROVED', 'REJECTED', 'INACTIVE');

-- Then modify the column to be VARCHAR first (in case it isn't already)
ALTER TABLE users MODIFY COLUMN e_status VARCHAR(20) NOT NULL;

-- Now standardize the values
UPDATE users SET e_status = UPPER(e_status);

-- Finally, convert to ENUM
ALTER TABLE users MODIFY COLUMN e_status ENUM('PENDING', 'APPROVED', 'REJECTED', 'INACTIVE') NOT NULL; 