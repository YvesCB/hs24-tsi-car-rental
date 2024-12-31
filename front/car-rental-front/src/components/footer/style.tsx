/* General Footer Styling */
.footer {
  background - color: var(--color - background);
  color: var(--color - text - light);
  padding: 20px;
  text - align: center;
  border - top: 1px solid var(--color - secondary - light);
}

/* Container for content */
.footer - container {
  max - width: 1200px;
  margin: 0 auto;
  display: flex;
  flex - direction: column;
  align - items: center;
  gap: 16px;
}

/* Footer Navigation */
.footer - nav ul {
  display: flex;
  gap: 24px;
  padding: 0;
  margin: 0;
  list - style: none;
}

.footer - nav ul li a {
  text - decoration: none;
  color: var(--color - secondary);
  font - size: 14px;
  transition: color 0.3s;
}

.footer - nav ul li a:hover {
  color: var(--color - secondary - light);
}

/* Responsive Design */
@media(min - width: 768px) {
    .footer - container {
    flex - direction: row;
    justify - content: space - between;
  }

    .footer - nav ul {
    justify - content: center;
  }
}
