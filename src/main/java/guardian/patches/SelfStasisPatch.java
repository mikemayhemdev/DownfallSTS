package guardian.patches;


import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import guardian.GuardianMod;
import guardian.orbs.StasisOrb;
import javassist.CtBehavior;

public class SelfStasisPatch {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Guardian:UIOptions").TEXT;

    @SpirePatch(
            clz = UseCardAction.class,
            method = SpirePatch.CLASS
    )
    public static class Fields
    {
        public static SpireField<StasisOrb> stasis = new SpireField<>(()->null);
    }

    @SpirePatch(
            clz = UseCardAction.class,
            method = SpirePatch.CONSTRUCTOR,
            paramtypez = { AbstractCard.class, AbstractCreature.class }
    )
    public static class PutInStasis
    {
        @SpirePostfixPatch
        public static void awayItGoes(UseCardAction __instance, AbstractCard card, AbstractCreature target)
        {
            if (card.hasTag(GuardianMod.SELFSTASIS) || card.hasTag(GuardianMod.SELFSTASISONCE))
            {
                card.tags.remove(GuardianMod.SELFSTASISONCE);

                if (GuardianMod.canSpawnStasisOrb()) {
                    if (!AbstractDungeon.player.hasEmptyOrb()) {
                        //GuardianMod.logger.info("passed has empty orb");
                        for (AbstractOrb o : AbstractDungeon.player.orbs) {
                            if (!(o instanceof StasisOrb)) {
                                //GuardianMod.logger.info("found non-stasis orb");
                                AbstractDungeon.player.orbs.remove(o);
                                AbstractDungeon.player.orbs.add(0, o);
                                //AbstractDungeon.player.evokeOrb(); the channel action should evoke the orb.
                                break;
                            }
                        }
                    }

                    StasisOrb orb = new StasisOrb(card, AbstractDungeon.player.hand, true);
                    Fields.stasis.set(__instance, orb);
                    AbstractDungeon.actionManager.addToTop(new ChannelAction(orb));
                } else {
                    if (!AbstractDungeon.player.hasEmptyOrb()) {
                        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[5], true));
                    }
                }
            }
        }
    }


    @SpirePatch(
            clz = UseCardAction.class,
            method = "update"
    )
    public static class DontMoveStasisedCards
    {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "targetCard", "duration" }
        )
        public static SpireReturn<?> SelfStasis(UseCardAction __instance, AbstractCard targetCard, @ByRef float[] duration)
        {
            if (Fields.stasis.get(__instance) != null)
            {
                AbstractDungeon.player.cardInUse = null;
                targetCard.exhaustOnUseOnce = false;
                targetCard.dontTriggerOnUseCard = false;
                AbstractDungeon.actionManager.addToBottom(new HandCheckAction());

                //Special check: If the card has already been accelerated out of stasis again, set freeToPlayOnce back to true
                //(it's set to false just slightly above this patch)
                if (Fields.stasis.get(__instance).passiveAmount <= 0)
                {
                    targetCard.freeToPlayOnce = true;
                }

                duration[0] -= Gdx.graphics.getDeltaTime();
                if (duration[0] < 0.0f)
                    __instance.isDone = true;

                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "purgeOnUse");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}