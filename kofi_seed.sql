-- =============================================================
-- KOFI CTRL — Script de população para desenvolvimento
-- Ordem de inserção respeita dependências de FK:
--   1. produtos  (sem FK)
--   2. pedidos   (sem FK)
--   3. itens_pedido (FK → pedidos + produtos)
-- =============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- -------------------------------------------------------------
-- 1. PRODUTOS
-- Categorias válidas: Bebidas, Lanches, Acompanhamentos, Sobremesas
-- Imagens apenas das que funcionam confirmadas
-- -------------------------------------------------------------
TRUNCATE TABLE itens_pedido;
TRUNCATE TABLE pedidos;
TRUNCATE TABLE produtos;

INSERT INTO produtos (nome, descricao, preco, estoque_atual, tempo_preparo_min, disponivel, url_imagem, categoria) VALUES
('Espresso',          'Café concentrado, encorpado e aromático.',          6.50,  30, 3,  true,  'https://images.unsplash.com/photo-1510591509098-f4fdc6d0ff04?w=400', 'bebida'),
('Cappuccino',        'Espresso com leite vaporizado e espuma cremosa.',   9.00,  25, 5,  true,  'https://images.unsplash.com/photo-1534778101976-62847782c213?w=400', 'bebida'),
('Latte',             'Espresso suave com bastante leite vaporizado.',     9.50,  20, 5,  true,  'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&q=80', 'bebida'),
('Cold Brew',         'Café coado a frio por 12h, servido com gelo.',     11.00,  15, 1,  true,  'https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400', 'bebida'),
('Matcha Latte',      'Chá matcha com leite vaporizado e mel.',           13.00,  10, 6,  true,  'https://images.unsplash.com/photo-1536256263959-770b48d82b0a?w=400', 'bebida'),
('Suco de Laranja',   'Suco natural espremido na hora.',                   9.00,  10, 4,  true,  'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&q=80', 'bebida'),
('Água com Gás',      'Água mineral gaseificada, 500ml.',                  5.00,  40, 1,  true,  'https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?w=400&q=80', 'bebida'),
('Croissant Simples', 'Massa folhada amanteigada, assado na hora.',        8.00,  20, 8,  true,  'https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=400', 'lanche'),
('Croissant Presunto','Croissant recheado com presunto e queijo.',        12.00,  15, 10, true,  'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&q=80', 'lanche'),
('Tapioca Recheada',  'Tapioca com queijo coalho e tomate seco.',         14.00,   8, 12, true,  'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&q=80', 'lanche'),
('Pão de Queijo',     'Pão de queijo mineiro, quentinho.',                 5.00,  30, 8,  true,  'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&q=80', 'acompanhamento'),
('Mix de Nuts',       'Mix de castanhas e amêndoas tostadas.',             8.00,  20, 1,  true,  'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400&q=80', 'acompanhamento'),
('Brownie',           'Brownie de chocolate belga com nozes.',            10.00,  12, 2,  true,  'https://images.unsplash.com/photo-1606313564200-e75d5e30476c?w=400', 'sobremesa'),
('Cookie',            'Cookie de gotas de chocolate, crocante por fora.',  7.00,  25, 2,  true,  'https://images.unsplash.com/photo-1551024601-bec78aea704b?w=400&q=80', 'sobremesa'),
('Muffin Blueberry',  'Muffin artesanal com blueberries frescas.',         9.50,   8, 3,  false, 'https://images.unsplash.com/photo-1551024601-bec78aea704b?w=400&q=80', 'sobremesa');

-- -------------------------------------------------------------
-- 2. PEDIDOS
-- -------------------------------------------------------------
INSERT INTO pedidos (numero_pedido, status, forma_pagamento, valor_total, criado_em, observacao) VALUES
('#0001', 'PRONTO',   'PIX',            18.50, NOW() - INTERVAL 90 MINUTE, NULL),
('#0002', 'PRONTO',   'CARTAO_DEBITO',  21.00, NOW() - INTERVAL 75 MINUTE, 'Sem açúcar no cappuccino'),
('#0003', 'PRONTO',   'DINHEIRO',        9.50, NOW() - INTERVAL 60 MINUTE, NULL),
('#0004', 'PENDENTE', 'CARTAO_CREDITO', 32.00, NOW() - INTERVAL 20 MINUTE, 'Pedido para viagem'),
('#0005', 'PENDENTE', 'PIX',            14.50, NOW() - INTERVAL 10 MINUTE, NULL),
('#0006', 'PENDENTE', 'DINHEIRO',       22.50, NOW() -  INTERVAL 5 MINUTE, 'Sem gelo no cold brew'),
('#0007', 'PENDENTE', 'PIX',            29.00, NOW() -  INTERVAL 2 MINUTE, NULL);

-- -------------------------------------------------------------
-- 3. ITENS_PEDIDO
-- IDs assumem AUTO_INCREMENT a partir de 1 (banco limpo)
-- -------------------------------------------------------------

-- Pedido #0001 (id=1): Espresso + Brownie
INSERT INTO itens_pedido (quantidade, preco_unitario, pedido_id, produto_id) VALUES
(1,  6.50, 1, 1),
(1, 10.00, 1, 13);

-- Pedido #0002 (id=2): Cappuccino x2 + Cookie
INSERT INTO itens_pedido (quantidade, preco_unitario, pedido_id, produto_id) VALUES
(2,  9.00, 2, 2),
(1,  7.00, 2, 14);

-- Pedido #0003 (id=3): Latte
INSERT INTO itens_pedido (quantidade, preco_unitario, pedido_id, produto_id) VALUES
(1,  9.50, 3, 3);

-- Pedido #0004 (id=4): Cold Brew + Croissant Presunto + Matcha Latte
INSERT INTO itens_pedido (quantidade, preco_unitario, pedido_id, produto_id) VALUES
(1, 11.00, 4, 4),
(1, 12.00, 4, 9),
(1, 13.00, 4, 5);

-- Pedido #0005 (id=5): Croissant Simples + Pão de Queijo x2
INSERT INTO itens_pedido (quantidade, preco_unitario, pedido_id, produto_id) VALUES
(1,  8.00, 5, 8),
(2,  5.00, 5, 11);

-- Pedido #0006 (id=6): Cappuccino + Tapioca Recheada
INSERT INTO itens_pedido (quantidade, preco_unitario, pedido_id, produto_id) VALUES
(1,  9.00, 6, 2),
(1, 14.00, 6, 10);

-- Pedido #0007 (id=7): Espresso + Matcha Latte + Brownie + Muffin Blueberry
INSERT INTO itens_pedido (quantidade, preco_unitario, pedido_id, produto_id) VALUES
(1,  6.50, 7, 1),
(1, 13.00, 7, 5),
(1, 10.00, 7, 13),
(1,  9.50, 7, 15);

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================
-- Resumo do seed:
--   produtos      → 15 registros (1 indisponível: Muffin Blueberry)
--   pedidos       → 7  registros (3 PRONTO, 4 PENDENTE)
--   itens_pedido  → 16 registros distribuídos nos 7 pedidos
-- =============================================================