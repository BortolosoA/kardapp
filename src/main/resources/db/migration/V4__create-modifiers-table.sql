CREATE TABLE modifier_groups (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    menu_item_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    min_select INTEGER NOT NULL,
    max_select INTEGER NOT NULL,
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id) ON DELETE CASCADE
);
CREATE TABLE modifiers (
     id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
     modifier_group_id UUID NOT NULL,
     price_delta DECIMAL(12,2) NOT NULL,
     FOREIGN KEY (modifier_group_id) REFERENCES modifier_groups(id) ON DELETE CASCADE
);
