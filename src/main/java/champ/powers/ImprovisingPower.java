package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.cards.AbstractChampCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.patches.NoDiscardField;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

public class ImprovisingPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("ImprovisingPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/Improvising84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/Improvising32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ImprovisingPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    private int timesUsed = 0;

    @Override
    public void atStartOfTurn() {
        timesUsed = 0;
        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.exhaust && !card.purgeOnUse) {
            boolean returnCard = false;
            if (card.hasTag(ChampMod.COMBO) && timesUsed < amount) {
                if (card.hasTag(ChampMod.COMBOBERSERKER)) {
                    returnCard = true;
                }
                if (card.hasTag(ChampMod.COMBODEFENSIVE)) {
                    returnCard = true;
                }

            }
            if (returnCard) {
                flash();
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        NoDiscardField.noDiscard.set(card, true);
                        NoDiscardField.freeCard = true;
                    }
                });
                timesUsed++;
                updateDescription();
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) description = DESCRIPTIONS[0] + (amount - timesUsed) + DESCRIPTIONS[1];
        else description = DESCRIPTIONS[2] + amount + DESCRIPTIONS[3] + (amount - timesUsed) + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ImprovisingPower(amount);
    }
}