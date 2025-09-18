package downfall.vfx.campfire;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;
import slimebound.SlimeboundMod;

import java.util.Iterator;

public class BustKeyEffect extends AbstractGameEffect {
    private static final float DURATION = 2.0f;
    private int numCards;
    private int shownCards;
    private String msgShown;
    private AbstractCard.CardRarity rarity;
    private Color screenColor;
    private boolean pickCard = false;
    private boolean rewardDone = false;

    public BustKeyEffect(int cards, int cardsToShow, AbstractCard.CardRarity raritySelected, String msg) {
        this.numCards = cards;
        this.shownCards = cardsToShow;
        this.rarity = raritySelected;
        this.msgShown = msg;
        this.screenColor = AbstractDungeon.fadeColor.cpy();
        this.duration = 1.5F;
        this.screenColor.a = 0.0F;
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }


    public void update() {
        if (!AbstractDungeon.isScreenUp) {
            this.duration -= Gdx.graphics.getDeltaTime();
            this.updateBlackScreenColor();
        }

        if (this.duration < 1.0F && !AbstractDungeon.isScreenUp && AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && !pickCard) {
            pickCard = true;
            CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (int i = 0; i < shownCards; ++i) {
                AbstractCard card = AbstractDungeon.getCard(rarity).makeCopy();
                boolean containsDupe = true;

                while (true) {
                    Iterator var6;
                    while (containsDupe) {
                        containsDupe = false;
                        var6 = group.group.iterator();

                        while (var6.hasNext()) {
                            AbstractCard c = (AbstractCard) var6.next();
                            if (c.cardID.equals(card.cardID)) {
                                containsDupe = true;
                                card = AbstractDungeon.getCard(rarity).makeCopy();
                                break;
                            }
                        }
                    }

                    if (group.contains(card)) {
                        --i;
                    } else {
                        var6 = AbstractDungeon.player.relics.iterator();

                        while (var6.hasNext()) {
                            AbstractRelic r = (AbstractRelic) var6.next();
                            r.onPreviewObtainCard(card);
                        }

                        group.addToBottom(card);
                    }
                    break;
                }
            }

            AbstractDungeon.gridSelectScreen.open(group, numCards, msgShown, false, false, true, false);

        }


        if (this.duration < 0.5F && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && pickCard && !rewardDone) {
            rewardDone = true;
            AbstractCard c = ((AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeCopy();
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() >= 2) {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.4F, (float) Settings.HEIGHT * 0.4F));
                c = ((AbstractCard) AbstractDungeon.gridSelectScreen.selectedCards.get(1)).makeCopy();
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH * 0.6F, (float) Settings.HEIGHT * 0.6F));
            } else {
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
            }
            this.duration = 5F; //this is super long but the crash happens after this point and before 5 seconds are up.

        }

        if (this.duration < 0.0F) {
            this.isDone = true;
            if (CampfireUI.hidden) {
                AbstractRoom.waitTimer = 0.0F;
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    ((RestRoom) AbstractDungeon.getCurrRoom()).campfireUI.reopen();
                    // there was a bug with the fire sound persisting and I'm not sure why,
                    // so this is basically a randomly thrown out preventative measure.
                    ((RestRoom) AbstractDungeon.getCurrRoom()).cutFireSound();

                }
            }

        }

    }



    private void updateBlackScreenColor() {
        if (this.duration > 1.0F) {
            this.screenColor.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - 1.0F) * 2.0F);
        } else {
            this.screenColor.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / 1.5F);
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, (float) Settings.WIDTH, (float) Settings.HEIGHT);
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID && pickCard && !rewardDone) {
            AbstractDungeon.gridSelectScreen.render(sb);
        }

    }

    @Override
    public void dispose() {
    }
}