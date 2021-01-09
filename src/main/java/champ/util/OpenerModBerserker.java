package champ.util;

import basemod.abstracts.AbstractCardModifier;
import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.stances.NeutralStance;
import slimebound.SlimeboundMod;

public class OpenerModBerserker extends AbstractCardModifier {


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + CardCrawlGame.languagePack.getUIString(ChampMod.makeID("OpenerMod")).TEXT[0];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {

        card.tags.add(ChampMod.OPENER);
        card.tags.add(ChampMod.OPENERBERSERKER);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        //SlimeboundMod.logger.info("Switching to Berserker (CardMod)");
        if (!(AbstractDungeon.player.stance.ID.equals(UltimateStance.STANCE_ID)))
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction(BerserkerStance.STANCE_ID));
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof OnOpenerSubscriber) ((OnOpenerSubscriber) r).onOpener(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
        }
        super.onUse(card, target, action);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new OpenerModBerserker();
    }
}
