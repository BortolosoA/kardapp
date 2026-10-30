CREATE TABLE menus (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    restaurant_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    is_active BOOLEAN NOT NULL,
    available_from TIME,
    available_to TIME,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE
);

CREATE TABLE allergens (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE categories (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    menu_id UUID NOT NULL,
    sort_order INTEGER NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menus(id)
);

CREATE TABLE menu_items (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(250),
    category_id UUID NOT NULL,
    price DECIMAL(12,2) NOT NULL,
    image_url VARCHAR(300),
    is_available BOOLEAN NOT NULL,
    archived BOOLEAN NOT NULL DEFAULT FALSE,
    sort_order INTEGER NOT NULL,
    CONSTRAINT menu_items_archived_unavailable CHECK (NOT archived OR NOT is_available),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE menu_item_allergens (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    menu_item_id UUID NOT NULL,
    allergen_id UUID NOT NULL,
    FOREIGN KEY (menu_item_id) REFERENCES menu_items(id),
    FOREIGN KEY (allergen_id) REFERENCES allergens(id)
);
