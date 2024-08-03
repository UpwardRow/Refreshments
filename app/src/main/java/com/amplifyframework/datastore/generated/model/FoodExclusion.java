package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasOne;
import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the FoodExclusion type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "FoodExclusions", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.DELETE, ModelOperation.UPDATE })
})
public final class FoodExclusion implements Model {
  public static final QueryField ID = field("FoodExclusion", "id");
  public static final QueryField DIETS = field("FoodExclusion", "diets");
  public static final QueryField INGREDIENTS = field("FoodExclusion", "ingredients");
  public static final QueryField ACCOUNT_ID = field("FoodExclusion", "accountId");
  public static final QueryField ACCOUNT_FIRST_NAME = field("FoodExclusion", "accountFirstName");
  public static final QueryField ACCOUNT_LAST_NAME = field("FoodExclusion", "accountLastName");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") List<String> diets;
  private final @ModelField(targetType="String") List<String> ingredients;
  private final @ModelField(targetType="ID", isRequired = true) String accountId;
  private final @ModelField(targetType="Account") @HasOne(associatedWith = "id", type = Account.class) Account account = null;
  private final @ModelField(targetType="String") String accountFirstName;
  private final @ModelField(targetType="String") String accountLastName;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public List<String> getDiets() {
      return diets;
  }
  
  public List<String> getIngredients() {
      return ingredients;
  }
  
  public String getAccountId() {
      return accountId;
  }
  
  public Account getAccount() {
      return account;
  }
  
  public String getAccountFirstName() {
      return accountFirstName;
  }
  
  public String getAccountLastName() {
      return accountLastName;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private FoodExclusion(String id, List<String> diets, List<String> ingredients, String accountId, String accountFirstName, String accountLastName) {
    this.id = id;
    this.diets = diets;
    this.ingredients = ingredients;
    this.accountId = accountId;
    this.accountFirstName = accountFirstName;
    this.accountLastName = accountLastName;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      FoodExclusion foodExclusion = (FoodExclusion) obj;
      return ObjectsCompat.equals(getId(), foodExclusion.getId()) &&
              ObjectsCompat.equals(getDiets(), foodExclusion.getDiets()) &&
              ObjectsCompat.equals(getIngredients(), foodExclusion.getIngredients()) &&
              ObjectsCompat.equals(getAccountId(), foodExclusion.getAccountId()) &&
              ObjectsCompat.equals(getAccountFirstName(), foodExclusion.getAccountFirstName()) &&
              ObjectsCompat.equals(getAccountLastName(), foodExclusion.getAccountLastName()) &&
              ObjectsCompat.equals(getCreatedAt(), foodExclusion.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), foodExclusion.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDiets())
      .append(getIngredients())
      .append(getAccountId())
      .append(getAccountFirstName())
      .append(getAccountLastName())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("FoodExclusion {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("diets=" + String.valueOf(getDiets()) + ", ")
      .append("ingredients=" + String.valueOf(getIngredients()) + ", ")
      .append("accountId=" + String.valueOf(getAccountId()) + ", ")
      .append("accountFirstName=" + String.valueOf(getAccountFirstName()) + ", ")
      .append("accountLastName=" + String.valueOf(getAccountLastName()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static AccountIdStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static FoodExclusion justId(String id) {
    return new FoodExclusion(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      diets,
      ingredients,
      accountId,
      accountFirstName,
      accountLastName);
  }
  public interface AccountIdStep {
    BuildStep accountId(String accountId);
  }
  

  public interface BuildStep {
    FoodExclusion build();
    BuildStep id(String id);
    BuildStep diets(List<String> diets);
    BuildStep ingredients(List<String> ingredients);
    BuildStep accountFirstName(String accountFirstName);
    BuildStep accountLastName(String accountLastName);
  }
  

  public static class Builder implements AccountIdStep, BuildStep {
    private String id;
    private String accountId;
    private List<String> diets;
    private List<String> ingredients;
    private String accountFirstName;
    private String accountLastName;
    @Override
     public FoodExclusion build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new FoodExclusion(
          id,
          diets,
          ingredients,
          accountId,
          accountFirstName,
          accountLastName);
    }
    
    @Override
     public BuildStep accountId(String accountId) {
        Objects.requireNonNull(accountId);
        this.accountId = accountId;
        return this;
    }
    
    @Override
     public BuildStep diets(List<String> diets) {
        this.diets = diets;
        return this;
    }
    
    @Override
     public BuildStep ingredients(List<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }
    
    @Override
     public BuildStep accountFirstName(String accountFirstName) {
        this.accountFirstName = accountFirstName;
        return this;
    }
    
    @Override
     public BuildStep accountLastName(String accountLastName) {
        this.accountLastName = accountLastName;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, List<String> diets, List<String> ingredients, String accountId, String accountFirstName, String accountLastName) {
      super.id(id);
      super.accountId(accountId)
        .diets(diets)
        .ingredients(ingredients)
        .accountFirstName(accountFirstName)
        .accountLastName(accountLastName);
    }
    
    @Override
     public CopyOfBuilder accountId(String accountId) {
      return (CopyOfBuilder) super.accountId(accountId);
    }
    
    @Override
     public CopyOfBuilder diets(List<String> diets) {
      return (CopyOfBuilder) super.diets(diets);
    }
    
    @Override
     public CopyOfBuilder ingredients(List<String> ingredients) {
      return (CopyOfBuilder) super.ingredients(ingredients);
    }
    
    @Override
     public CopyOfBuilder accountFirstName(String accountFirstName) {
      return (CopyOfBuilder) super.accountFirstName(accountFirstName);
    }
    
    @Override
     public CopyOfBuilder accountLastName(String accountLastName) {
      return (CopyOfBuilder) super.accountLastName(accountLastName);
    }
  }
  
}
