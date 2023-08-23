# API REST - Commerce

## Utilizando Componentes e Injeção de Dependência
Na construção do nosso projeto, seguimos o princípio de dividir nosso código em componentes independentes e coesos. Essa abordagem permite uma maior facilidade em realizar manutenções e aproveitar partes do código em diferentes partes da aplicação. A injeção de dependência foi aplicada para garantir que esses componentes se comuniquem de maneira eficiente, melhorando a modularidade e a escalabilidade do nosso sistema.

Exemplo de injeção de dependencias:

    @Service
    public class OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order order = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {

        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : dto.getItems()){

            Product product = productRepository.getReferenceById(itemDTO.getProductId());

            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());

            order.getItems().add(item);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }

## Modelo de Domínio e ORM
Nosso foco foi manter a consistência com as regras de negócios presentes no modelo de domínio. Para isso, utilizamos o ORM (Object-Relational Mapping) para preservar os dados de forma otimizada no banco de dados H2, que foi empregado para realizar testes. O uso do ORM simplificou a interação entre nossas entidades de domínio e as tabelas do banco de dados, garantindo a integridade e eficiência dos dados armazenados:

![image](https://github.com/lfernandex/Commerce/assets/106842103/c651c01b-a618-4d6f-9ffa-f7fe4071348f)

## Camadas e CRUD
Seguindo o padrão de camadas, organizamos os componentes do sistema de forma hierárquica. Cada camada tem uma responsabilidade específica e os componentes de uma camada só dependem de componentes da mesma camada ou de camadas inferiores. A estrutura ficou da seguinte forma:

![image](https://github.com/lfernandex/Commerce/assets/106842103/39be8d68-da8a-409f-a459-29652f05bf1d)


### DTO (Data Transfer Object)
A utilização de DTOs (Objetos de Transferência de Dados) se tornou uma parte essencial do nosso projeto. Os DTOs nos permitem transferir dados entre diferentes partes da aplicação, melhorando a segurança e permitindo um controle mais preciso sobre quais informações são compartilhadas com os usuários.

## CRUD, SQL e JPA
Nossa aplicação incorpora as operações fundamentais de um banco de dados (CRUD): criar (Create), ler (Read), atualizar (Update) e deletar (Delete) produtos. Isso foi implementado considerando as permissões de acesso dos diferentes perfis de usuário. Além disso, o JPA (Java Persistence API) foi empregado para facilitar a interação com o banco de dados, permitindo a criação, leitura, atualização e exclusão de registros de forma mais eficiente.

Para aqueles que desejam buscar produtos por nome, integramos funcionalidades de consulta SQL utilizando o JPA. Isso permite que os usuários realizem pesquisas específicas no banco de dados, melhorando a usabilidade da aplicação.

    @RestController
    @RequestMapping(value = "/products")
    public class ProductController {
    
    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductMinDTO>> findAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable){
        Page<ProductMinDTO> dto = service.findAll(name, pageable);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDTO> insert(@Valid @RequestBody ProductDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

## Login e Controle de Acesso
A segurança é fundamental em nosso projeto. Utilizamos os princípios do OAuth2 (Protocolo de Autorização) e JWT (Token de Acesso JSON) para estabelecer um sistema de autenticação de login e autorização de solicitações. O Spring Security atua como uma camada de proteção, assegurando que somente usuários autorizados tenham acesso às funcionalidades de acordo com seus perfis.

![image](https://github.com/lfernandex/Commerce/assets/106842103/97542f17-02b2-44c9-8a97-9c5b405c7496)


## Conclusão
Nossa abordagem detalhada incluiu a utilização eficiente de componentes, a manutenção do modelo de domínio, a aplicação do padrão de camadas, a implementação de operações CRUD, o uso de DTOs, consultas SQL e a implementação de um sistema seguro de login e controle de acesso. Isso resultou em um projeto coeso, escalável e seguro, pronto para atender às necessidades dos nossos usuários.

