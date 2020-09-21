package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.cards.AbstractChampCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.patches.NoDiscardField;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class ImprovisingPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("ImprovisingPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Again84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Again32.png");
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
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        boolean returnCard = false;
        if (card.rawDescription.contains("champ:Combo") && timesUsed < amount) {
            if (card.rawDescription.contains("champ:Gladiator champ:Combo") && !(AbstractChampCard.gcombo())) {
                returnCard = true;
            }
            if (card.rawDescription.contains("champ:Defensive champ:Combo") && !(AbstractChampCard.dcombo())) {
                returnCard = true;
            }
            if (card.rawDescription.contains("champ:Berserker champ:Combo") && !(AbstractChampCard.bcombo())) {
                returnCard = true;
            }
        }
        if (returnCard) {
            flash();
            NoDiscardField.noDiscard.set(card, true);
            card.freeToPlayOnce = true;
            timesUsed++;
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