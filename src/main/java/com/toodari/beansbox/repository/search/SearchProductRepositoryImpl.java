package com.toodari.beansbox.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.toodari.beansbox.entity.Product;
import com.toodari.beansbox.entity.QProduct;
import com.toodari.beansbox.entity.QProductImage;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class SearchProductRepositoryImpl extends QuerydslRepositorySupport implements SearchProductRepository {

    public SearchProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public Product search1() {

        log.info("search1....................");

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> jpqlQuery = from(product);
        jpqlQuery.leftJoin(productImage).on(productImage.product.eq(product));
        jpqlQuery.select(product).where(product.pactive.eq(1));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(product, productImage);
        tuple.groupBy(product);

        jpqlQuery.select(product).where(product.pnum.eq(1L));

        log.info("----------------------------------");
        log.info(tuple);
        log.info("----------------------------------");

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage.............................");

        QProduct product = QProduct.product;
        QProductImage productImage = QProductImage.productImage;

        JPQLQuery<Product> jpqlQuery = from(product);
        jpqlQuery.leftJoin(productImage).on(productImage.product.eq(product));
        jpqlQuery.select(product).where(product.pactive.eq(1));
        JPQLQuery<Tuple> tuple = jpqlQuery.select(product, productImage);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = product.pnum.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            if (type.contains("n")) {
                conditionBuilder.or(product.pname.contains(keyword));
            }
            if (type.contains("c")) {
                conditionBuilder.or(product.pcat.contains(keyword));
            }

            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Product.class, "product");

            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(product);

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();

        log.info("COUNT: " + count);

        return new PageImpl<Object[]>(
                result.stream().map(t -> t.toArray()).collect(Collectors.toList()),
                pageable,
                count);
    }
}
