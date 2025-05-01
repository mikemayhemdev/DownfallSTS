package slimebound.cards;


import automaton.actions.AddToFuncAction;
import automaton.cards.BranchBlock;
import automaton.cards.BranchHit;
import collector.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.actions.OctoChoiceAction;
import downfall.cards.OctoChoiceCard;
import downfall.util.OctopusCard;
import slimebound.SlimeboundMod;

import slimebound.actions.BuffCidStrengthAction;
import slimebound.actions.BuffPikeStrengthAction;
import slimebound.actions.MassRepurposeAction;
import slimebound.actions.TriggerSlimeAttacksAction;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.PotencyPower;
import sneckomod.SneckoMod;

import java.util.ArrayList;


public class MassRepurpose extends AbstractSlimeboundCard implements OctopusCard {
    public static final String ID = "Slimebound:MassRepurpose";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/morpheverything.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public MassRepurpose() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = 5;
        this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "MassRepurpose.png");

        this.tags.add(SneckoMod.BANNEDFORSNECKO);
       // this.tags.add(SneckoMod.BANNEDFORSNECKO);

//         this.tags.add(CardTags.HEALING);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        String imagePath = "slimeboundResources/images/cards/morpheverything.png";
        cardList.add(new OctoChoiceCard("Slimebound:MassRepurposeCid", EXTENDED_DESCRIPTION[2], imagePath, EXTENDED_DESCRIPTION[0], -1, -1, CardType.SKILL));
        cardList.add(new OctoChoiceCard("Slimebound:MassRepurposePike", EXTENDED_DESCRIPTION[3], imagePath, EXTENDED_DESCRIPTION[1], -1, -1, CardType.SKILL));
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "Slimebound:MassRepurposeCid": {
                addToBot(new BuffCidStrengthAction(magicNumber));
                break;
            }
            case "Slimebound:MassRepurposePike": {
                addToBot(new BuffPikeStrengthAction(magicNumber));

                break;
            }
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
        }
    }
}


