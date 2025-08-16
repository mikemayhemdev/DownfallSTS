package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import awakenedOne.relics.EyeOfTheOccult;
import awakenedOne.relics.OnAwakenRelic;
import champ.powers.GladiatorFormPower;
import champ.powers.ParryPower;
import champ.relics.RageAmulet;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import guardian.powers.RevengePower;

import java.util.ArrayList;

import static hermit.util.Wiz.removePower;

public class Branch extends AbstractBronzeCard implements OctopusCard {

    public final static String ID = makeID("Branch");

    private static final int DAMAGE = 7;
    private static final int BLOCK = 6;

    public Branch() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        exhaust = true;
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("Branch.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        String pathNormalHit = "bronzeResources/images/cards/BranchHit.png";
        String pathJokeHit = "bronzeResources/images/cards/joke/BranchAttack.png";
        String pathNormalBlock = "bronzeResources/images/cards/BranchBlock.png";
        String pathJokeBlock = "bronzeResources/images/cards/BranchBlock.png";
        String pathHit = (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(ID, false)) ? pathJokeHit : pathNormalHit;
        String pathBlock = (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(ID, false)) ? pathJokeBlock : pathNormalBlock;
        cardList.add(new OctoChoiceCard("bronze:BranchHit", EXTENDED_DESCRIPTION[0], pathHit, EXTENDED_DESCRIPTION[1], damage, -1, CardType.ATTACK));
        cardList.add(new OctoChoiceCard("bronze:BranchBlock", EXTENDED_DESCRIPTION[2], pathBlock, EXTENDED_DESCRIPTION[3], -1, block, CardType.SKILL));
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "bronze:BranchHit": {
                AbstractCard q = new BranchBlock();
                if (upgraded) q.upgrade();
                att(new AddToFuncAction(q, null));
                att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, card.baseDamage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

                if (AbstractDungeon.player.hasPower(GladiatorFormPower.POWER_ID)) {
                    GladiatorFormPower revengePower = (GladiatorFormPower) AbstractDungeon.player.getPower(GladiatorFormPower.POWER_ID);

                    if (revengePower != null) {
                        revengePower.onSpecificTriggerBranch();
                    }
                }

                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    if (r instanceof RageAmulet) {
                        ((RageAmulet) r).onSpecificTrigger();
                    }
                }

                atb(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, VigorPower.POWER_ID));

                break;
            }
            case "bronze:BranchBlock": {
                AbstractCard q = new BranchHit();
                if (upgraded) q.upgrade();
                att(new AddToFuncAction(q, null));
                att(new GainBlockAction(AbstractDungeon.player, card.baseBlock));
            }
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}
