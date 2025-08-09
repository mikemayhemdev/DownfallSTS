package awakenedOne.actions;

import awakenedOne.cards.altDimension.*;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import java.util.ArrayList;
import java.util.Collections;

public class GetDimensionCardsAction extends AbstractGameAction {
    private int cardCount = 0;
    private int cardsToSelect = 0;
    private boolean retrieveCard = false;

    private ArrayList<AbstractCard> cards = new ArrayList<>();
    private ArrayList<Integer> numbers = new ArrayList<>();

    public GetDimensionCardsAction(int count, int grabCount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        cardCount = count;
        cardsToSelect = grabCount;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (cards.size() == 0) {
                for (int i = 0; i <= 8; i++) {
                    numbers.add(i);
                }
                Collections.shuffle(numbers, AbstractDungeon.cardRandomRng.random);

                for (int i = 0; i < cardCount; ++i) {
                    AbstractCard card = null;
                    switch (numbers.get(0)) {
                        case 0: {
                            card = new Crusher();
                            break;
                        }
                        case 1: {
                            card = new Daggerstorm();
                            break;
                        }
                        case 2: {
                            card = new ManaShield();
                            break;
                        }
                        case 3: {
                            card = new Minniegun();
                            break;
                        }
                        case 4: {
                            card = new PackRat();
                            break;
                        }
                        case 5: {
                            card = new Scheme();
                            break;
                        }
                        case 6: {
                            card = new SignInBlood();
                            break;
                        }
                        case 7: {
                            card = new SpreadingSpores();
                            break;
                        }
                        case 8: {
                            card = new TheEncyclopedia();
                            break;
                        }
                    }

                    cards.add(card);
                    numbers.remove(0);
                }
            }

            AbstractDungeon.cardRewardScreen.customCombatOpen(cards, CardRewardScreen.TEXT[1], false);
            this.tickDuration();
        } else {
            if (!this.retrieveCard) {
                if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                    AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();

                    disCard.current_x = -1000.0F * Settings.scale;
                    if (AbstractDungeon.player.hand.size() < BaseMod.MAX_HAND_SIZE) {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    } else {
                        AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                    }

                    cardsToSelect--;
                    cards.remove(AbstractDungeon.cardRewardScreen.discoveryCard);
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                }

                this.retrieveCard = true;
                if (cardsToSelect > 0) {
                    if (cards.size() > 0) {
                        this.duration = Settings.ACTION_DUR_FAST;
                        this.isDone = false;
                        this.retrieveCard = false;
                    } else {
                        this.isDone = true;
                        this.tickDuration();
                    }
                } else {
                    this.isDone = true;
                    this.tickDuration();
                }

            }
        }
    }

}