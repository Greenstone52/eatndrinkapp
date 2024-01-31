package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Order;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Review;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CustomerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OrderRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.ReviewRepository;
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
            return null;
        }else{
            reviews = reviewRepository.findReviewsByCustomerId(id);
            return reviews.stream().map(review -> new ReviewResponse(review)).collect(Collectors.toList());
        }

    }

    public List<ReviewResponse> getAllTheReviewsOfTheRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);

        List<Review> reviewList = new ArrayList<>();

        if(restaurant == null){
            return null;
        }else{
            reviewList = reviewRepository.findReviewsByRestaurantId(restaurantId);
            return reviewList.stream().map(review -> new ReviewResponse(review)).collect(Collectors.toList());
        }

    }

    public String addAReview(Long id, Review review, Long restaurantId){
        Customer customer = findCustomer(id);

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        Review existReview = reviewRepository.findReviewByCustomerIdAndRestaurantId(id,restaurantId).orElse(null);

        // The purpose is that giving permission to a customer make comment a restaurant with a constraint
        // which is that he/she only make a comment for the restaurants which were ordered something by him/her.
        List<Order> ordersForTheCustomer =  orderRepository.findOrdersByCustomerId(id);

        boolean isReal = false;

        for (int i = 0; i < ordersForTheCustomer.size(); i++) {
            if(ordersForTheCustomer.get(i).getRestaurant() == restaurant){
                isReal = true;
                break;
            }
        }

        if (isReal) {
            if(existReview == null){
                if(customer == null && restaurant == null){
                    return "Please enter an available id and restaurant.";
                }
                else if(customer == null){
                    return "Please enter an available id.";
                }
                else if(restaurant == null){
                    return "Please enter an available restaurant.";
                }else{
                    Review newReview = new Review();
                    newReview.setCustomer(customer);
                    newReview.setText(review.getText());
                    newReview.setTitle(review.getTitle());
                    newReview.setPoint(review.getPoint());
                    newReview.setRestaurant(review.getRestaurant());

                    reviewRepository.save(newReview);

                    return "Your review is saved successfully.";
                }
            }else{
                return "You have already added a review for this restaurant.";
            }
        }else{
            return "You cannot make a review for a restaurant you did not order anything.";
        }


    }

    public String updateReview(Long id, Review review,Long restaurantId){
        //Customer customer = findCustomer(id);

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        Review oldReview = reviewRepository.findReviewByCustomerIdAndRestaurantId(id,restaurantId).orElse(null);

        if(oldReview != null){
            oldReview.setRestaurant(review.getRestaurant());
            oldReview.setText(review.getText());
            oldReview.setTitle(review.getTitle());
            oldReview.setPoint(review.getPoint());

            reviewRepository.save(oldReview);
            return "Your review is updated!";
        }else{
            return "You have not a review for this restaurant.";
        }


    }

    public String deleteReview(Long id,Long restaurantId){
        //Customer customer = findCustomer(id);

        Review review = reviewRepository.findReviewByCustomerIdAndRestaurantId(id,restaurantId).orElse(null);

        if(review != null){
            reviewRepository.deleteById(review.getId());
            return "Your review was removed successfully.";
        }
        else{
            return "There is no such a review.";
        }

    }
}
