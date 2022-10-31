package gremlin.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gremlin.cards.TagTeam;

public class TagTeamwork extends AbstractGremlinRelic {
    public static final String ID = getID("TagTeamwork");
    private static final RelicStrings strings = CardCrawlGame.languagePack.getRelicStrings(ID);
    private static final AbstractRelic.RelicTier TIER = RelicTier.SHOP;
    private static final String IMG = "relics/tag_teamwork.png";
    private static final AbstractRelic.LandingSound SOUND = LandingSound.MAGICAL;

    public TagTeamwork() {
        super(ID, IMG, TIER, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return strings.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new TagTeam(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new TagTeam(), (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    @Override
    public void onTrigger() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }
}

