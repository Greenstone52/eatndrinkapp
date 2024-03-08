package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Order;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Review;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.*;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CustomerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OrderRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.ReviewRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.ReviewCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.ReviewResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {

    private ReviewRepository reviewRepository;
    private RestaurantRepository restaurantRepository;
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<ReviewResponse> getAllTheReviewsOfTheCustomer(Long id){
        Customer customer = findCustomer(id);

        List<Review> reviews;
        if(customer == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }else{
            reviews = reviewRepository.findReviewsByCustomerId(id);
            return reviews.stream().map(review -> new ReviewResponse(review)).collect(Collectors.toList());
        }

    }

    public List<ReviewResponse> getAllTheReviewsOfTheRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<Review> reviewList;

        if(restaurant == null){
            throw new RestaurantNotFoundException("There is no such a restaurant.");
        }else{
            reviewList = reviewRepository.findReviewsByRestaurantId(restaurantId);
            return reviewList.stream().map(review -> new ReviewResponse(review)).collect(Collectors.toList());
        }

    }

    public String addAReview(Long customerId, ReviewCreateRequest review, Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("There is no such an order."));
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("There is no such a customer."));

        if(order.getCustomer() == customer){
            Review newReview = new Review();
            newReview.setCustomer(order.getCustomer());
            newReview.setRestaurant(order.getRestaurant());
            newReview.setText(review.getText());
            newReview.setPoint(review.getPoint());
            newReview.setTitle(review.getTitle());
            newReview.setOrder(order);

            try{
                reviewRepository.save(newReview);
            }catch (DataIntegrityViolationException exception){
                throw new ReviewAlreadyExistsException("You already set a review for this order.");
            }catch (ConstraintViolationException exception){
                throw new InvalidValueException("Please enter valid values for point(1-5).");
            }

            return "Your review is saved successfully.";

        }else{
            throw new IllegalReviewRequestException("You cannot make a review about an order you did not make.");
        }

    }

    public String updateReview(Long id, ReviewCreateRequest review,Long reviewId){

        Review oldReview = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("You have not a review for this restaurant."));
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("There is no such an customer."));

            if(oldReview.getCustomer().getId() == id){
                oldReview.setText(review.getText());
                oldReview.setTitle(review.getTitle());
                oldReview.setPoint(review.getPoint());

                try{
                    reviewRepository.save(oldReview);
                }catch (RuntimeException exception){
                    throw new InvalidValueException("Please enter valid values for point(1-5).");
                }

                return "Your review is updated!";

            }else{
                throw new ReviewIsNotYoursException("Forbidden request.");
            }

    }

    public String deleteReview(Long id,Long reviewId){

        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("There is no such a review."));
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("There is no such a customer."));

            if(review.getCustomer() == customer){
                reviewRepository.deleteById(review.getId());
                return "Your review was removed successfully.";
            }else{
                throw new ReviewIsNotYoursException("Forbidden request.");
            }
    }
}
