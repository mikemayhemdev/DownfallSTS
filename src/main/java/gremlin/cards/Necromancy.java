package gremlin.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.actions.NecromancyAction;
import gremlin.characters.GremlinCharacter;
import gremlin.orbs.GremlinStandby;
import gremlin.powers.BangPower;
import gremlin.powers.WizPower;
import sneckomod.SneckoMod;

import static gremlin.GremlinMod.WIZARD_GREMLIN;

public class Necromancy extends AbstractGremlinCard {
    public static final String ID = getID("Necromancy");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/necromancy.png";

    private static final AbstractCard.CardType TYPE = CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.RARE;
    private static final AbstractCard.CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 1;
    private static final int MAGIC = 10;
    private static final int UPGRADE_BONUS = 5;
    private static final int VOODOO = 5;

    public Necromancy()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;

        this.exhaust = true;
        this.tags.add(AbstractCard.CardTags.HEALING);
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        this.tags.add(WIZARD_GREMLIN);
        setBackgrounds();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, WizPower.POWER_ID));
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, BangPower.POWER_ID));
        AbstractDungeon.actionManager.addToBottom(new NecromancyAction(magicNumber));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (!p.hasPower(WizPower.POWER_ID) || p.getPower(WizPower.POWER_ID).amount < VOODOO)
        {
            this.cantUseMessage = strings.EXTENDED_DESCRIPTION[0];
            return false;
        } else if(!deadGrem()){
            this.cantUseMessage = strings.EXTENDED_DESCRIPTION[1];
            return false;
        } else if(!hasRoom()){
            this.cantUseMessage = strings.EXTENDED_DESCRIPTION[2];
            return false;
        }
        return true;
    }

    private boolean deadGrem(){
        int count = 0;
        for(AbstractOrb orb : AbstractDungeon.player.orbs){
            if(orb instanceof GremlinStandby){
                count++;
            }
        }
        int total = 4;
        if(AbstractDungeon.player instanceof GremlinCharacter){
            total -= ((GremlinCharacter) AbstractDungeon.player).mobState.numEnslaved();
        }
        return (count!=total);
    }

    private boolean hasRoom() {
        return AbstractDungeon.player.maxOrbs >= 4;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
        }
    }
}
