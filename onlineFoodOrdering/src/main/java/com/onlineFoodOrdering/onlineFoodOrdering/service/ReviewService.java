package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Order;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Review;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.CustomerNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.RestaurantNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.ReviewNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CustomerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OrderRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.ReviewRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.ReviewCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.ReviewResponse;
import lombok.AllArgsConstructor;
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

        List<Review> reviews = new ArrayList<>();
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

    public String addAReview(Long customerId, ReviewCreateRequest review, Long restaurantId){
        Customer customer = findCustomer(customerId);

        if(customer == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("There is no such a restaurant"));
        //Review existReview = reviewRepository.findReviewByCustomerIdAndRestaurantId(customerId,restaurantId).orElse(null);

        // The purpose is that giving permission to a customer make comment a restaurant with a constraint
        // which is that he/she only make a comment for the restaurants which were ordered something by him/her.
        List<Order> ordersForTheCustomer =  orderRepository.findOrdersByCustomerId(customerId);

        boolean isOrderedBefore = false;

        for (int i = 0; i < ordersForTheCustomer.size(); i++) {
            if(ordersForTheCustomer.get(i).getRestaurant() == restaurant){
                isOrderedBefore = true;
                break;
            }
        }

        //if(customer == null && restaurant == null){
        //    return "Please enter an available id and restaurant.";
        //}
        //else if(customer == null){
        //    return "There is no such a customer.";
        //}
        //else if(restaurant == null){
        //    return "Please enter an available restaurant.";
        //}



        if (isOrderedBefore) {
            //if(existReview == null){
                    Review newReview = new Review();
                    newReview.setCustomer(customer);
                    newReview.setText(review.getText());
                    newReview.setTitle(review.getTitle());
                    newReview.setPoint(review.getPoint());
                    newReview.setRestaurant(restaurant);
                    reviewRepository.save(newReview);

                    return "Your review is saved successfully.";
            //}else{
            //    return "You have already added a review for this restaurant.";
            //}
        }else{
            return "You cannot make a review for a restaurant you did not order anything.";
        }


    }

    public String updateReview(Long id, ReviewCreateRequest review,Long reviewId){

        //Review oldReview = reviewRepository.findReviewByCustomerIdAndRestaurantId(id,restaurantId).orElse(null);
        Review oldReview = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("You have not a review for this restaurant."));
            if(oldReview.getCustomer().getId() == id){
                oldReview.setText(review.getText());
                oldReview.setTitle(review.getTitle());
                oldReview.setPoint(review.getPoint());
                reviewRepository.save(oldReview);
                return "Your review is updated!";
            }else{
                return "Forbidden request.";
            }

    }

    public String deleteReview(Long id,Long reviewId){
        //Customer customer = findCustomer(id);

        //Review review = reviewRepository.findReviewByCustomerIdAndRestaurantId(id,restaurantId).orElse(null);

        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("There is no such a review."));

            if(review.getCustomer().getId() == id){
                reviewRepository.deleteById(review.getId());
                return "Your review was removed successfully.";
            }else{
                return "Forbidden request.";
            }
    }
}
