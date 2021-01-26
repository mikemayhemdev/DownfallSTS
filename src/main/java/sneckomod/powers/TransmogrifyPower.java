package sneckomod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.cards.Transmogrify;
import sneckomod.util.TransmogrifyLinkedReward;

public class TransmogrifyPower extends AbstractPower implements NonStackablePower {

    public static final String POWER_ID = SneckoMod.makeID("Transmogrifying");

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Transmogrifying84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/Transmogrifying32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private AbstractRelic relicTransmoged;

    public TransmogrifyPower(AbstractRelic relic) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.relicTransmoged = relic;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = relicTransmoged.name + DESCRIPTIONS[0] + relicTransmoged.tier + DESCRIPTIONS[1];
    }

    @Override
    public void onVictory() {
        RewardItem original = new RewardItem(Transmogrify.returnTrueRandomScreenlessRelic(relicTransmoged.tier));
        TransmogrifyLinkedReward newrelic = new TransmogrifyLinkedReward(original);
        TransmogrifyLinkedReward newrelic2 = new TransmogrifyLinkedReward(newrelic, relicTransmoged);

        AbstractDungeon.getCurrRoom().rewards.add(newrelic);
        AbstractDungeon.getCurrRoom().rewards.add(newrelic2);
    }

}