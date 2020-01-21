package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

import java.util.ArrayList;

public class FutureUpgradePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("FutureUpgradePower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power32.png");

    public FutureUpgradePower(final int amount) {
        this.name = "Post-Combat Experience";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onVictory() {
        for (int i = 0; i < amount; i++) {
            ArrayList<AbstractCard> possibleCards = new ArrayList<>();// 38
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {// 40
                    possibleCards.add(c);// 41
                }
            }

            if (!possibleCards.isEmpty()) {// 45
                AbstractCard card = (AbstractCard) possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));// 46
                card.upgrade();// 47
                AbstractDungeon.player.bottledCardUpgradeCheck(card);// 48
                card.upgrade();
                float x = MathUtils.random(0.1F, 0.9F) * (float) Settings.WIDTH;
                float y = MathUtils.random(0.2F, 0.8F) * (float) Settings.HEIGHT;
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy(), x, y));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(x, y));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1)
            description = "At the end of combat, #yUpgrade #b" + amount + " random card in your deck.";
        else
            description = "At the end of combat, #yUpgrade #b" + amount + " random cards in your deck.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new FutureUpgradePower(amount);
    }
}