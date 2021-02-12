package champ.powers;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.cardmods.ExhaustMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class StrikeOfGeniusPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("StrikeOfGeniusPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/StrikeOfGenius84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/StrikeOfGenius32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StrikeOfGeniusPower(final int amount) {
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

    public void atStartOfTurn() {
        for (int i = 0; i < this.amount; ++i) {
            ArrayList<AbstractCard> qCardList = new ArrayList<>();
            for (AbstractCard r : CardLibrary.getAllCards())
                if (r.color == AbstractDungeon.player.getCardColor() && !UnlockTracker.isCardLocked(r.cardID)&& !r.hasTag(AbstractCard.CardTags.HEALING) && r.hasTag(AbstractCard.CardTags.STRIKE) && r.type == AbstractCard.CardType.ATTACK && !(r instanceof AbstractUnknownCard)) qCardList.add(r);
            AbstractCard l = qCardList.get(AbstractDungeon.cardRandomRng.random(qCardList.size() - 1)).makeStatEquivalentCopy();
            l.freeToPlayOnce = true;
            if (!l.exhaust) CardModifierManager.addModifier(l, new ExhaustMod());
            this.addToBot(new MakeTempCardInHandAction(l));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new StrikeOfGeniusPower(amount);
    }
}