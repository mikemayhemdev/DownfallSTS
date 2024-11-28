package collector.patches.PyrePatches;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import champ.vfx.SelfSpikesEffect;
import collector.cardmods.PyreMod;
import collector.cards.Bonfire;
import collector.cards.OnPyreCard;
import collector.powers.OnPyrePower;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static collector.util.Wiz.att;

@SpirePatch(clz = AbstractPlayer.class, method = "useCard")
public class PyreAdditionalCostPatch {
    private static UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("collector:PyreCostSpendScreen");

    public static void Postfix(AbstractPlayer p, AbstractCard c, AbstractMonster m, int energyOnUse) {
        if (CardModifierManager.hasModifier(c, PyreMod.ID)) {
            for (AbstractCardModifier r : CardModifierManager.getModifiers(c, PyreMod.ID)) {
                if (r instanceof PyreMod) {
                    System.out.println("DEBUG: Checking card " + c.name + " (cardID: " + c.cardID + ", upgraded: " + c.upgraded + ")");
                    if (c.cardID.equals("collector:Bonfire") && !c.upgraded) {
                        // Bonfire- Logic
                        System.out.println("DEBUG: This is an unupgraded Bonfire-. Card: " + c);
                        if (!AbstractDungeon.player.hand.isEmpty()) {
                            AbstractCard randomCard = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                            System.out.println("DEBUG: Selected random card: " + randomCard.name);
                            for (AbstractPower pow : AbstractDungeon.player.powers) {
                                if (pow instanceof OnPyrePower) {
                                    System.out.println("DEBUG: Applying OnPyrePower to card: " + randomCard.name);
                                    ((OnPyrePower) pow).onPyre(randomCard);
                                }
                            }
                            if (c instanceof OnPyreCard) {
                                System.out.println("DEBUG: Applying OnPyreCard logic to card: " + randomCard.name);
                                ((OnPyreCard) c).onPyred(randomCard);
                            }
                            att(new ExhaustSpecificCardAction(randomCard, AbstractDungeon.player.hand));
                        }
                    } else {
                        // Standard Logic
                        System.out.println("DEBUG: This is NOT an unupgraded Bonfire-. Card: " + c);
                        att(new SelectCardsInHandAction("Select a card to Pyre", (cards) -> {
                            System.out.println("DEBUG: Selected card: " + cards.get(0).name);
                            for (AbstractPower pow : AbstractDungeon.player.powers) {
                                if (pow instanceof OnPyrePower) {
                                    System.out.println("DEBUG: Applying OnPyrePower to card: " + cards.get(0).name);
                                    ((OnPyrePower) pow).onPyre(cards.get(0));
                                }
                            }
                            if (c instanceof OnPyreCard) {
                                System.out.println("DEBUG: Applying OnPyreCard logic to card: " + cards.get(0).name);
                                ((OnPyreCard) c).onPyred(cards.get(0));
                            }
                            att(new ExhaustSpecificCardAction(cards.get(0), AbstractDungeon.player.hand));
                        }));
                    }
                }
            }
        }
    }
}