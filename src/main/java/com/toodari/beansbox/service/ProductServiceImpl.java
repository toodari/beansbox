package com.toodari.beansbox.service;

import com.toodari.beansbox.dto.PageRequestDTO;
import com.toodari.beansbox.dto.PageResultDTO;
import com.toodari.beansbox.dto.ProductDTO;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ProductDTO productDTO) {
        log.info("DTO......................");
        log.info(productDTO);

        Product product = modelMapper.map(productDTO, Product.class);

        log.info(product);
        productRepository.save(product);

        return product.getPnum();
    }

    @Override
    public PageResultDTO<ProductDTO, Product> getList(PageRequestDTO requestDTO){

        Pageable pageable = requestDTO.getPageable(Sort.by("pnum").descending());

        Page<Product> result = productRepository.findAll(pageable);

        Function<Product, ProductDTO> fn = (product -> modelMapper.map(product, ProductDTO.class));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public ProductDTO read(Long pnum) {
        Optional<Product> result = productRepository.findById(pnum);
        return result.isPresent()? modelMapper.map(result.get(), ProductDTO.class): null; //modelmapper에서 entityToDto는 뒤에 DTO
    }

    @Override
    public void modify(ProductDTO dto) {
        Optional<Product> result = productRepository.findById(dto.getPnum());

            ProductDTO productDTO = modelMapper.map(result.get(), ProductDTO.class);
//            Product entity = result.get();
            productDTO.setPname(dto.getPname());
            productDTO.setPcat(dto.getPcat());
            productDTO.setPcost(dto.getPcost());
            productDTO.setPprice(dto.getPprice());

            Product product = modelMapper.map(productDTO, Product.class);

            productRepository.save(product);

    }

    @Override
    public Long copy(ProductDTO productDTO) {
        log.info("DTO......................");
        log.info(productDTO);
        Product product = modelMapper.map(productDTO, Product.class);

        log.info(product);
        productRepository.save(product);

        return productDTO.getPnum();
    }

    @Override
    public void remove(Long pnum) {
        productRepository.deleteById(pnum);
    }

}
