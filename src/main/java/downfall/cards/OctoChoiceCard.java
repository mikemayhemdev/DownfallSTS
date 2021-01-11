package downfall.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

public class OctoChoiceCard extends CustomCard {
    private static final int COST = -2;
    private String IMG = null;

    private AbstractCard card1;
    private AbstractCard card2;
    private AbstractCard card3;

    private AbstractCard prev1;
    private AbstractCard prev2;
    private AbstractCard prev3;

    public OctoChoiceCard(String id, String name, String IMG, String description, int dmg, int blk, CardType type) {
        super(id, name, IMG, COST, description, type, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.IMG = IMG;
        baseDamage = dmg;
        baseBlock = blk;
    }

    public OctoChoiceCard(String id, String name, String IMG, String description) {
        super(id, name, IMG, COST, description, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.IMG = IMG;
    }

    public OctoChoiceCard(String id, String name, String IMG, String description, CardColor color) {
        super(id, name, IMG, COST, description, CardType.SKILL, color, CardRarity.SPECIAL, CardTarget.NONE);
        this.IMG = IMG;
    }

    public OctoChoiceCard(String id, String name, String IMG, String description, CardColor color, CardType type) {
        super(id, name, IMG, COST, description, type, color, CardRarity.SPECIAL, CardTarget.NONE);
        this.IMG = IMG;
    }

    // Future Vex will benefit greatly by making it so there's not 100,000 constructors here...

    public OctoChoiceCard(String id, String name, String IMG, String description, AbstractCard prev1, AbstractCard prev2, AbstractCard prev3) {
        super(id, name, IMG, COST, description, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.IMG = IMG;
        card1 = prev1;
        card2 = prev2;
        card3 = prev3;
    }

    @Override
    public void hover() {
        if (card1 != null){
            prev1 = card1;
        }
        if (card2 != null){
            prev2 = card2;
        }
        if (card3 != null){
            prev3 = card3;
        }
        super.hover();
    }

    @Override
    public void unhover() {
        super.unhover();
        prev1 = null;
        prev2 = null;
        prev3 = null;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }

    @Override
    public void renderCardTip(SpriteBatch sb) {
        super.renderCardTip(sb);

        //Removes the preview when the player is manipulating the card or if the card is locked
        if (isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode))) {
            return;
        }

        float drawScale = 0.5f;
        float yPosition1 = Settings.HEIGHT * 0.2F;

        float xPosition1 = Settings.WIDTH * 0.35F;
        float xPosition2 = Settings.WIDTH * 0.5F;
        float xPosition3 = Settings.WIDTH * 0.65F;

        if (prev1 != null) {
            AbstractCard card = prev1.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition1;
                card.current_y = yPosition1;
                card.render(sb);
            }
        }
        if (prev2 != null) {
            AbstractCard card = prev2.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition2;
                card.current_y = yPosition1;
                card.render(sb);
            }
        }
        if (prev3 != null) {
            AbstractCard card = prev3.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition3;
                card.current_y = yPosition1;
                card.render(sb);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new OctoChoiceCard(cardID, name, IMG, rawDescription);
    }
}