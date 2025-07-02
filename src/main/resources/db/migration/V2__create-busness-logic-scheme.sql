CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50),
    customer_id INT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_bookings_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
    CONSTRAINT unique_booking_customer UNIQUE (code, customer_id)
);

CREATE TABLE containers (
  id BIGSERIAL PRIMARY KEY,
  booking_id BIGINT NOT NULL,
  name VARCHAR(50) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE,

  CONSTRAINT fk_containers_booking FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
  CONSTRAINT unique_container_booking UNIQUE (name, booking_id)
);

CREATE TABLE orders (
  id BIGSERIAL PRIMARY KEY,
  booking_id BIGINT NOT NULL,
  purchase VARCHAR(50) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE,

  CONSTRAINT fk_orders_booking FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE,
  CONSTRAINT unique_order_booking UNIQUE (purchase, booking_id)
);

CREATE TABLE invoices (
  id BIGSERIAL PRIMARY KEY,
  order_id BIGINT NOT NULL,
  invoice VARCHAR(50) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE,

  CONSTRAINT fk_invoices_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
  CONSTRAINT unique_invoice_order UNIQUE (invoice, order_id)
);

CREATE INDEX idx_booking_code ON bookings (code);
CREATE INDEX idx_container_name ON containers (name);
CREATE INDEX idx_order_purchase ON orders (purchase);
CREATE INDEX idx_invoice ON invoices (invoice);